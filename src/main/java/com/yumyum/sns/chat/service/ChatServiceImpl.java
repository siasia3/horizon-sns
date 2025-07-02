package com.yumyum.sns.chat.service;

import com.yumyum.sns.chat.dto.ChatMessage;
import com.yumyum.sns.chat.dto.ChatResponse;
import com.yumyum.sns.chat.dto.ChatUpdateRequest;
import com.yumyum.sns.chat.entity.Chat;
import com.yumyum.sns.chat.entity.ChatRoom;
import com.yumyum.sns.chat.entity.ChatState;
import com.yumyum.sns.chat.repository.ChatRepository;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;

    //채팅 조회
    @Override
    @Transactional(readOnly = true)
    public List<ChatResponse> getChats(Long chatRoomId, Long lastChatId, int size) {

        ChatRoom chatRoom = chatRoomService.checkChatRoom(chatRoomId);
        List<Chat> chats = chatRepository.findChatsWithCursor(chatRoom.getId(), lastChatId, size);
        return chats.stream().map(ChatResponse::from).collect(Collectors.toList());
    }

    //채팅 생성
    @Override
    public ChatResponse createChat(ChatMessage message) {
        Member sender = memberService.getMemberById(message.getSenderId());
        ChatRoom chatRoom = chatRoomService.checkChatRoom(message.getChatRoomId());
        Chat chat = message.toEntity(sender, chatRoom, ChatState.SENT);
        return ChatResponse.from(chatRepository.save(chat));
    }

    @Override
    public ChatResponse updateChat(ChatUpdateRequest chatUpdateRequest) {
        return null;
    }

    @Override
    public void deleteChat(Long chatId) {

    }
}
