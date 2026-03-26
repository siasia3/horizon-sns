package com.yumyum.sns.notification.controller;

import com.yumyum.sns.notification.dto.NotificationDto;
import com.yumyum.sns.notification.service.NotificationService;
import com.yumyum.sns.notification.sse.SseEmitterManager;
import com.yumyum.sns.security.common.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final SseEmitterManager sseEmitterManager;

    // SSE 연결
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@AuthenticationPrincipal AuthMember authMember) {
        return sseEmitterManager.connect(authMember.getUserId());
    }

    // 알림 목록 조회
    @GetMapping
    public ResponseEntity<Slice<NotificationDto>> getNotifications(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(notificationService.getNotifications(authMember.getUserId(), lastId, size));
    }

    // 안읽음 개수
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@AuthenticationPrincipal AuthMember authMember) {
        return ResponseEntity.ok(notificationService.getUnreadCount(authMember.getUserId()));
    }

    // 개별 읽음 처리
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    // 전체 읽음 처리
    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@AuthenticationPrincipal AuthMember authMember) {
        notificationService.markAllAsRead(authMember.getUserId());
        return ResponseEntity.ok().build();
    }
}
