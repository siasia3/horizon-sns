package com.yumyum.sns.notification.service;

import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.service.MemberService;
import com.yumyum.sns.notification.NotificationType;
import com.yumyum.sns.notification.TargetType;
import com.yumyum.sns.notification.dto.NotificationDto;
import com.yumyum.sns.notification.entity.Notification;
import com.yumyum.sns.notification.event.NotificationEvent;
import com.yumyum.sns.notification.repository.NotificationRepository;
import com.yumyum.sns.notification.sse.SseEmitterManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private SseEmitterManager sseEmitterManager;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Member receiver;
    private Member sender;
    private Notification notification;

    @BeforeEach
    void setUp() {
        receiver = mock(Member.class);
        sender = mock(Member.class);
        given(receiver.getId()).willReturn(1L);
        given(sender.getId()).willReturn(2L);
        given(sender.getNickname()).willReturn("sender");
        given(sender.getProfileImage()).willReturn("profile.png");

        notification = new Notification(sender, receiver, NotificationType.LIKE, TargetType.POST, 10L);
    }

    @Test
    @DisplayName("saveAndSend - 알림 저장 및 SSE 전송 성공")
    void saveAndSend_success() {
        NotificationEvent event = new NotificationEvent(1L, 2L, NotificationType.LIKE, TargetType.POST, 10L);

        given(memberService.getMemberById(1L)).willReturn(receiver);
        given(memberService.getMemberById(2L)).willReturn(sender);
        given(notificationRepository.save(any())).willReturn(notification);

        notificationService.saveAndSend(event);

        then(notificationRepository).should().save(any(Notification.class));
        then(sseEmitterManager).should().send(eq(1L), any(NotificationDto.class));
    }

    @Test
    @DisplayName("saveAndSend - 예외 발생 시 조용히 실패 (로그만 남김)")
    void saveAndSend_exception_silentFail() {
        NotificationEvent event = new NotificationEvent(1L, 2L, NotificationType.COMMENT, TargetType.POST, 5L);

        given(memberService.getMemberById(1L)).willReturn(receiver);
        given(memberService.getMemberById(2L)).willReturn(sender);
        given(notificationRepository.save(any())).willThrow(new RuntimeException("DB 오류"));

        assertThatCode(() -> notificationService.saveAndSend(event))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("getNotifications - 커서 기반 알림 목록 반환")
    void getNotifications_returnSlice() {
        Slice<Notification> mockSlice = new SliceImpl<>(List.of(notification));
        given(notificationRepository.findNotificationsWithCursor(1L, null, 20)).willReturn(mockSlice);

        Slice<NotificationDto> result = notificationService.getNotifications(1L, null, 20);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNotiType()).isEqualTo(NotificationType.LIKE);
    }

    @Test
    @DisplayName("getUnreadCount - 읽지 않은 알림 개수 반환")
    void getUnreadCount_returnCount() {
        given(notificationRepository.countUnreadNotifications(1L)).willReturn(5L);

        Long count = notificationService.getUnreadCount(1L);

        assertThat(count).isEqualTo(5L);
    }

    @Test
    @DisplayName("markAsRead - 알림 읽음 처리")
    void markAsRead_success() {
        given(notificationRepository.findById(1L)).willReturn(Optional.of(notification));

        notificationService.markAsRead(1L);

        assertThat(notification.isRead()).isTrue();
        assertThat(notification.getReadAt()).isNotNull();
    }

    @Test
    @DisplayName("markAsRead - 존재하지 않는 알림 예외 발생")
    void markAsRead_notFound_throwsException() {
        given(notificationRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> notificationService.markAsRead(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("알림을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("markAllAsRead - 전체 알림 읽음 처리 위임")
    void markAllAsRead_success() {
        notificationService.markAllAsRead(1L);

        then(notificationRepository).should().markAllAsRead(1L);
    }
}