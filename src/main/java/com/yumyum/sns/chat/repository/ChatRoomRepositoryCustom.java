package com.yumyum.sns.chat.repository;

import com.yumyum.sns.chat.dto.DirectChatMemRequest;
import com.yumyum.sns.chat.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepositoryCustom {
    /**
     *
     * 1:1 채팅방이 존재하는지 아닌지 확인
     * @param directChatMemRequest 1:1 채팅 회원들
     * @return 채팅방
     */
    Optional<ChatRoom> findChatRoom(DirectChatMemRequest directChatMemRequest);
}
