package com.yumyum.sns.chat.dto;

import com.yumyum.sns.chat.entity.Chat;
import com.yumyum.sns.chat.entity.ChatRoom;
import com.yumyum.sns.chat.entity.ChatState;
import com.yumyum.sns.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    private Long chatId;   // 채팅 번호
    private MessageType type;   // 메시지 타입
    private Long chatRoomId;      // 방 번호
    private Long senderId;      // 보내는 사람
    private String message;     // 메시지
    private LocalDateTime createdAt;

    public Chat toEntity(Member member, ChatRoom chatRoom, ChatState chatState){
        return Chat.builder()
                .content(message)
                .state(chatState)
                .member(member)
                .chatroom(chatRoom)
                .build();
    }


}
