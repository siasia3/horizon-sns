package com.yumyum.sns.notification.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SseEmitterManager {

    // 유저별 SseEmitter 저장 (동시 접속 고려해서 ConcurrentHashMap)
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // SSE 연결
    public SseEmitter connect(Long memberId) {
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L); // 1시간 타임아웃

        emitters.put(memberId, emitter);

        // 연결 종료/타임아웃/에러 시 제거
        emitter.onCompletion(() -> emitters.remove(memberId));
        emitter.onTimeout(() -> emitters.remove(memberId));
        emitter.onError(e -> emitters.remove(memberId));

        // 연결 직후 더미 이벤트 (503 방지)
        try {
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e) {
            log.warn("SSE 초기 연결 이벤트 전송 실패. memberId={}", memberId, e);
            emitters.remove(memberId);
        }

        return emitter;
    }

    // 알림 푸시
    public void send(Long memberId, Object data) {
        SseEmitter emitter = emitters.get(memberId);
        if (emitter == null) {
            log.debug("SSE emitter 없음. memberId={}",memberId);
            return;
        }

        try {
            emitter.send(SseEmitter.event().name("notification").data(data));
        } catch (IOException e) {
            log.warn("SSE 전송 실패. memberId={}", memberId, e);
            emitters.remove(memberId);
        }
    }
}
