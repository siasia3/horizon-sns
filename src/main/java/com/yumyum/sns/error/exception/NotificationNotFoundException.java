package com.yumyum.sns.error.exception;

public class NotificationNotFoundException extends BaseNotFoundException{
    public NotificationNotFoundException(Long notificationId) {
        super("해당 댓글을 찾을 수 없습니다: " + notificationId);
    }
}
