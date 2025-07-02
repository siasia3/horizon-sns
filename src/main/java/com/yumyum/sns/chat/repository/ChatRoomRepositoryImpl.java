package com.yumyum.sns.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yumyum.sns.chat.dto.DirectChatMemRequest;
import com.yumyum.sns.chat.entity.ChatRoom;
import com.yumyum.sns.chat.entity.ChatRoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


import static com.yumyum.sns.chat.entity.QChatRoom.*;
import static com.yumyum.sns.chat.entity.QChatRoomMember.*;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    //1:1 채팅방 확인
    @Override
    public Optional<ChatRoom> findChatRoom(DirectChatMemRequest directChatMemRequest) {
        ChatRoom result = queryFactory
                .select(chatRoom)
                .from(chatRoom)
                .where(
                        chatRoom.chatroomType.eq(ChatRoomType.ONETOONE),
                        chatRoom.id.in(
                                queryFactory
                                        .select(chatRoomMember.chatroom.id)
                                        .from(chatRoomMember)
                                        .where(chatRoomMember.member.id.in(directChatMemRequest.getReceiverId(), directChatMemRequest.getSenderId()))
                                        .groupBy(chatRoomMember.chatroom.id)
                                        .having(chatRoomMember.member.id.countDistinct().eq(2L))
                        ))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
