package com.yumyum.sns.chat.dto;

import com.yumyum.sns.chat.entity.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomDto {

    public Long chatroomId;
    public LocalDateTime createAt;
    public List<Long> chatRoomMembers = new ArrayList<>();

    public static ChatRoomDto from(ChatRoom chatRoom) {
        return new ChatRoomDto(chatRoom.getId(),chatRoom.getCreatedAt());
    }

    public ChatRoomDto(Long chatRoomId, LocalDateTime createAt) {
        this.chatroomId = chatRoomId;
        this.createAt = createAt;
    }
}
