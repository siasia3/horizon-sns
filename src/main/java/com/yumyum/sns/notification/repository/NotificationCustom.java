package com.yumyum.sns.notification.repository;

import com.yumyum.sns.notification.entity.Notification;
import org.springframework.data.domain.Slice;

public interface NotificationCustom {

    /**
     * 커서 기반 알림 목록 조회
     * @param receiverId 수신자 pk
     * @param lastId 커서 id
     * @param size 크기
     * @return 알림 목록
     */
    Slice<Notification> findNotificationsWithCursor(Long receiverId, Long lastId, int size);

    /**
     * 안 읽은 알림 개수 조회
     * @param receiverId 수신자 pk
     * @return 안 읽은 알림 개수
     */
    Long countUnreadNotifications(Long receiverId);

    /**
     * 전체 읽음 처리
     * @param receiverId 수신자 pk
     */
    void markAllAsRead(Long receiverId);
}
