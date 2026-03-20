package com.yumyum.sns.notification.event;

import com.yumyum.sns.notification.NotificationType;
import com.yumyum.sns.notification.TargetType;
import com.yumyum.sns.notification.service.NotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationEventListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationEventListener notificationEventListener;

    @Test
    @DisplayName("handleNotification - 이벤트를 서비스로 위임")
    void handleNotification_delegatesToService() {
        NotificationEvent event = new NotificationEvent(1L, 2L, NotificationType.LIKE, TargetType.POST, 10L);

        notificationEventListener.handleNotification(event);

        then(notificationService).should().saveAndSend(event);
    }

    @Test
    @DisplayName("handleNotification - COMMENT 타입 이벤트 처리")
    void handleNotification_commentType() {
        NotificationEvent event = new NotificationEvent(3L, 4L, NotificationType.COMMENT, TargetType.POST, 20L);

        notificationEventListener.handleNotification(event);

        then(notificationService).should().saveAndSend(event);
    }

    @Test
    @DisplayName("handleNotification - REPLY 타입 이벤트 처리")
    void handleNotification_replyType() {
        NotificationEvent event = new NotificationEvent(5L, 6L, NotificationType.REPLY, TargetType.COMMENT, 30L);

        notificationEventListener.handleNotification(event);

        then(notificationService).should().saveAndSend(event);
    }
}
