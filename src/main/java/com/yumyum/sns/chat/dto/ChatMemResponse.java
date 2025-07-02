package com.yumyum.sns.chat.dto;

import com.yumyum.sns.chat.entity.ChatRoomMember;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMemResponse {
    private Long chatMemId;
    private Long memId;
    private Long chatroomId;
    private LocalDateTime joinedAt;

    public static ChatMemResponse from(ChatRoomMember chatRoomMember){
        return new ChatMemResponse(
                chatRoomMember.getId(),
                chatRoomMember.getMember().getId(),
                chatRoomMember.getChatroom().getId(),
                chatRoomMember.getJoinedAt());
    }

    public ChatMemResponse(Long chatMemId, Long memId, Long chatroomId, LocalDateTime joinedAt) {
        this.chatMemId = chatMemId;
        this.memId = memId;
        this.chatroomId = chatroomId;
        this.joinedAt = joinedAt;
    }
}
