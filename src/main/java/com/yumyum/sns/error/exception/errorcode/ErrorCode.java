package com.yumyum.sns.error.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Notification
    NOTIFICATION_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "본인의 알림만 삭제할 수 있습니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}