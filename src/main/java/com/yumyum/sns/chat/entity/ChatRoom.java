package com.yumyum.sns.chat.entity;

import com.yumyum.sns.chat.dto.ChatRoomDto;
import com.yumyum.sns.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatroom")
    private List<Chat> chatList = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom")
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ChatRoomType chatroomType;

    public ChatRoom(ChatRoomType chatroomType) {
        this.chatroomType = chatroomType;
    }

}


