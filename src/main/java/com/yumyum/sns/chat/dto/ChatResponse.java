package com.yumyum.sns.chat.dto;

import com.yumyum.sns.chat.entity.Chat;
import com.yumyum.sns.chat.entity.ChatState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatResponse {
    private Long chatId;
    private Long chatRoomId;
    private Long senderId;
    private String content;
    private ChatState state;
    private LocalDateTime createdAt;
    private String profilePath;
    private String nickname;

    public static ChatResponse from(Chat chat){
        return ChatResponse.builder()
                .chatId(chat.getId())
                .chatRoomId(chat.getChatroom().getId())
                .senderId(chat.getMember().getId())
                .content(chat.getContent())
                .state(chat.getState())
                .createdAt(chat.getCreatedAt())
                .profilePath(chat.getMember().getProfileImage())
                .nickname(chat.getMember().getNickname())
                .build();
    }
}
