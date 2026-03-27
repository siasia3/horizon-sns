package com.yumyum.sns.notification.dto;

import com.yumyum.sns.notification.NotificationType;
import com.yumyum.sns.notification.TargetType;
import com.yumyum.sns.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationDto {

    private Long notificationId;
    private Long senderId;
    private String senderNickname;
    private String senderProfileImage;
    private NotificationType notificationType;
    private TargetType targetType;
    private Long targetId;
    private boolean isRead;
    private LocalDateTime createdAt;

    public static NotificationDto from(Notification notification) {
        return NotificationDto.builder()
                .notificationId(notification.getId())
                .senderId(notification.getSender().getId())
                .senderNickname(notification.getSender().getNickname())
                .senderProfileImage(notification.getSender().getProfileImage())
                .notificationType(notification.getNotificationType())
                .targetType(notification.getTargetType())
                .targetId(notification.getTargetId())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
