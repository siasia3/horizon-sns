package com.yumyum.sns.notification.event;

import com.yumyum.sns.notification.NotificationType;
import com.yumyum.sns.notification.TargetType;

public record NotificationEvent(
        Long receiverId,
        Long senderId,
        NotificationType notificationType,
        TargetType targetType,
        Long targetId
) {}