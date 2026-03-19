package com.yumyum.sns.notification.service;

import com.yumyum.sns.notification.dto.NotificationDto;
import com.yumyum.sns.notification.event.NotificationEvent;
import org.springframework.data.domain.Slice;

public interface NotificationService {

    /**
     * 알림 저장 + SSE 푸시
     * @param event 알림 이벤트 정보
     */
    public void saveAndSend(NotificationEvent event);

    /**
     * 알림 목록 조회
     * @param receiverId 수신자 pk
     * @param lastId 커서 id
     * @param size 페이징 사이즈
     * @return 알림 목록 리스트
     */
    public Slice<NotificationDto> getNotifications(Long receiverId, Long lastId, int size);

    /**
     * 읽지 않은 알림 개수 조회
     * @param receiverId 수신자 pk
     * @return 읽지 않은 알림 개수
     */
    public Long getUnreadCount(Long receiverId);

    /**
     * 단일 읽음 상태 변경
     * @param notificationId 알림 pk
     */
    public void markAsRead(Long notificationId);

    /**
     * 전체 읽음 상태 변경
     * @param receiverId 수신자
     */
    public void markAllAsRead(Long receiverId);
}
