package com.yumyum.sns.notification.service;

import com.yumyum.sns.error.exception.custom.BusinessException;
import com.yumyum.sns.error.exception.errorcode.ErrorCode;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.service.MemberService;
import com.yumyum.sns.notification.event.NotificationEvent;
import com.yumyum.sns.notification.dto.NotificationDto;
import com.yumyum.sns.notification.entity.Notification;
import com.yumyum.sns.notification.repository.NotificationRepository;
import com.yumyum.sns.notification.sse.SseEmitterManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final MemberService memberService;
    private final SseEmitterManager sseEmitterManager;

    @Override
    @Transactional
    public void saveAndSend(NotificationEvent event) {
        Member receiver = memberService.getMemberById(event.receiverId());
        Member sender = memberService.getMemberById(event.senderId());

        Notification notification = new Notification(
                sender, receiver,
                event.notificationType(), event.targetType(), event.targetId()
        );

        try {
            notificationRepository.save(notification);
            sseEmitterManager.send(event.receiverId(), NotificationDto.from(notification));
        }catch (Exception e){
            log.error("알림 처리 실패. receiverId={}, event={}", event.receiverId(), event, e);
        }

    }

    //알림 목록 조회
    @Override
    public Slice<NotificationDto> getNotifications(Long receiverId, Long lastId, int size) {
        Slice<Notification> notifications = notificationRepository.findNotificationsWithCursor(receiverId, lastId, size);
        List<NotificationDto> dtos = notifications.getContent().stream()
                .map(NotificationDto::from)
                .toList();
        return new SliceImpl<>(dtos, PageRequest.of(0, size), notifications.hasNext());
    }

    //읽지 않은 알림 개수
    @Override
    public Long getUnreadCount(Long receiverId) {
        return notificationRepository.countUnreadNotifications(receiverId);
    }

    // 단일 알림 읽음 상태로 변경
    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("알림을 찾을 수 없습니다."));
        notification.markAsRead();
    }

    //전체 알림 읽음 상태로 변경
    @Override
    @Transactional
    public void markAllAsRead(Long receiverId) {
        notificationRepository.markAllAsRead(receiverId);
    }

    // 전체 알림 삭제 상태로 변경
    @Override
    @Transactional
    public void deleteAllNotifications(Long receiverId) {
        notificationRepository.deleteAllNotifications(receiverId);
    }

    // 단일 알림 삭제 상태로 변경
    @Override
    @Transactional
    public void deleteNotification(Long notificationId, Long receiverId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND));
        if (!notification.getReceiver().getId().equals(receiverId)) {
            throw new BusinessException(ErrorCode.NOTIFICATION_DELETE_FORBIDDEN);
        }
        notification.markAsDeleted();
    }
}
