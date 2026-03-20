package com.yumyum.sns.notification.controller;

import com.yumyum.sns.member.service.MemberService;
import com.yumyum.sns.notification.dto.NotificationDto;
import com.yumyum.sns.notification.service.NotificationService;
import com.yumyum.sns.notification.sse.SseEmitterManager;
import com.yumyum.sns.security.oauthjwt.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final JWTUtil jwtUtil;
    private final NotificationService notificationService;
    private final MemberService memberService;
    private final SseEmitterManager sseEmitterManager;

    // SSE 연결
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@CookieValue(name = "Authorization") String jwt) {
        String identifier = jwtUtil.getUsername(jwt);
        Long memberId = memberService.getMemberByIdentifier(identifier).getId();
        return sseEmitterManager.connect(memberId);
    }

    // 알림 목록 조회
    @GetMapping
    public ResponseEntity<Slice<NotificationDto>> getNotifications(
            @CookieValue(name = "Authorization") String jwt,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "20") int size) {
        // memberId 가져오기
        String identifier = jwtUtil.getUsername(jwt);
        Long memberId = memberService.getMemberByIdentifier(identifier).getId();
        return ResponseEntity.ok(notificationService.getNotifications(memberId, lastId, size));
    }

    // 안읽음 개수
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@CookieValue(name = "Authorization") String jwt) {
        String identifier = jwtUtil.getUsername(jwt);
        Long memberId = memberService.getMemberByIdentifier(identifier).getId();
        return ResponseEntity.ok(notificationService.getUnreadCount(memberId));
    }

    // 개별 읽음 처리
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    // 전체 읽음 처리
    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@CookieValue(name = "Authorization") String jwt) {
        Long memberId = memberService.getMemberByIdentifier(jwtUtil.getUsername(jwt)).getId();
        notificationService.markAllAsRead(memberId);
        return ResponseEntity.ok().build();
    }
}
