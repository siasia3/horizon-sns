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
    private String message;

    public static NotificationDto from(Notification notification) {
        String message = buildMessage(notification.getNotificationType(),notification.getSender().getNickname());

        return NotificationDto.builder()
                .notificationId(notification.getId())
                .senderId(notification.getSender().getId())
                .senderNickname(notification.getSender().getNickname())
                .senderProfileImage(notification.getSender().getProfileImage())
                .message(message)
                .notificationType(notification.getNotificationType())
                .targetType(notification.getTargetType())
                .targetId(notification.getTargetId())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    private static String buildMessage(NotificationType type, String senderNickname) {
        return switch (type) {
            case COMMENT       -> senderNickname + "님이 댓글을 남겼습니다.";
            case REPLY -> senderNickname + "님이 대댓글을 남겼습니다.";
            case LIKE -> senderNickname + "님이 좋아요를 눌렀습니다.";
        };
    }
}
