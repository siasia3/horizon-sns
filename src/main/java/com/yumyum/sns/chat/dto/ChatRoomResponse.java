package com.yumyum.sns.chat.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomResponse {

    public Long chatroomId;
    public LocalDateTime createAt;
    public List<Long> chatRoomMembers = new ArrayList<>();

    public ChatRoomResponse(Long chatroomId, LocalDateTime createAt, List<Long> chatRoomMembers) {
        this.chatroomId = chatroomId;
        this.createAt = createAt;
        this.chatRoomMembers = chatRoomMembers;
    }
}
