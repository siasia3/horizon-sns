package com.yumyum.sns.chat.repository;

import com.yumyum.sns.chat.entity.Chat;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChatRepositoryCustom {

    /**
     * 채팅들을 cursor 방식으로 조회
     * @param chatRoomId 채팅방 id
     * @param lastChatId 마지막 채팅 id
     * @param size 조회할 개수
     * @return 조회된 채팅들
     */
    List<Chat> findChatsWithCursor(Long chatRoomId, Long lastChatId, int size);

}
