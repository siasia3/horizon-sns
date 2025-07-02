package com.yumyum.sns.chat.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yumyum.sns.chat.entity.Chat;
import com.yumyum.sns.chat.entity.QChat;
import com.yumyum.sns.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yumyum.sns.chat.entity.QChat.*;
import static com.yumyum.sns.member.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    //채팅 조회
    @Override
    public List<Chat> findChatsWithCursor(Long chatRoomId, Long lastChatId, int size) {

        return queryFactory
                .selectFrom(chat)
                .join(chat.member,member)
                .where(chatRoomIdEq(chatRoomId), chatIdGt(lastChatId))
                .orderBy(chat.createdAt.asc())
                .limit(size)
                .fetch();
    }

    public BooleanExpression chatRoomIdEq(Long chatRoomId) {
        return chat.chatroom.id.eq(chatRoomId);
    }

    public BooleanExpression chatIdGt(Long cursorId) {
        return cursorId != null ? chat.id.gt(cursorId) : null;
    }
}
