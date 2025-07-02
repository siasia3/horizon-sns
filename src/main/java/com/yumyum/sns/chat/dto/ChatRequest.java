package com.yumyum.sns.chat.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class ChatRequest {
    public Long chatRoomId;
    public Long lastChatId;
    public Pageable pageable;
}
