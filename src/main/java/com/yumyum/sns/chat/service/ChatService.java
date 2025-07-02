package com.yumyum.sns.chat.service;

import com.yumyum.sns.chat.dto.ChatMessage;
import com.yumyum.sns.chat.dto.ChatResponse;
import com.yumyum.sns.chat.dto.ChatUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {

    /**
     * 채팅방 번호와 마지막 채팅ID를 기준으로 일정 개수의 전송된 채팅을 조회(Cursor 기반 페이징)
     * @param chatRoomId 채팅방 번호
     * @param lastChatId 마지막 채팅 ID
     * @param size 조회할 개수
     * @return 특정 채팅방에서 작성된 채팅들
     */
    List<ChatResponse> getChats(Long chatRoomId, Long lastChatId, int size);

    /**
     * 채팅 insert
     * @param message 전송된 채팅
     * @return 작성된 채팅 정보
     */
    ChatResponse createChat(ChatMessage message);

    /**
     * 채팅 수정(update)
     * @param chatUpdateRequest 수정할 채팅
     * @return 수정된 채팅 정보
     */
    ChatResponse updateChat(ChatUpdateRequest chatUpdateRequest);

    /**
     * 채팅 소프트 삭제(delete)
     * @param chatId 삭제될 채팅id
     */
    void deleteChat(Long chatId);






}
