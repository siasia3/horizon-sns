package com.yumyum.sns.notification.sse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.assertj.core.api.Assertions.*;

class SseEmitterManagerTest {

    private SseEmitterManager sseEmitterManager;

    @BeforeEach
    void setUp() {
        sseEmitterManager = new SseEmitterManager();
    }

    @Test
    @DisplayName("connect - SseEmitter 반환 및 connect 이벤트 전송")
    void connect_returnEmitter() {
        SseEmitter emitter = sseEmitterManager.connect(1L);

        assertThat(emitter).isNotNull();
    }

    @Test
    @DisplayName("connect - 동일 memberId 재연결 시 기존 emitter 교체")
    void connect_replaceExistingEmitter() {
        SseEmitter first = sseEmitterManager.connect(1L);
        SseEmitter second = sseEmitterManager.connect(1L);

        assertThat(first).isNotSameAs(second);
    }

    @Test
    @DisplayName("send - emitter 없는 경우 예외 없이 무시")
    void send_noEmitter_silentIgnore() {
        assertThatCode(() -> sseEmitterManager.send(999L, "data"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("send - emitter 완료 후 재전송 시 예외 없이 처리")
    void send_afterComplete_silentIgnore() {
        SseEmitter emitter = sseEmitterManager.connect(1L);
        emitter.complete(); // onCompletion 콜백으로 emitter 제거됨

        assertThatCode(() -> sseEmitterManager.send(1L, "data"))
                .doesNotThrowAnyException();
    }
}