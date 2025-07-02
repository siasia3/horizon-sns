package com.yumyum.sns.chat.service;

import com.yumyum.sns.chat.dto.ChatMemResponse;
import com.yumyum.sns.chat.dto.DirectChatMemRequest;
import com.yumyum.sns.chat.entity.ChatRoom;
import com.yumyum.sns.chat.entity.ChatRoomMember;
import com.yumyum.sns.chat.repository.ChatRoomMemberRepository;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomMemberServiceImpl implements ChatRoomMemberService{

    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberService memberService;

    //채팅방 멤버 insert
    @Override
    public List<ChatMemResponse> createChatRoomMem(List<Member> chatMems, ChatRoom chatRoom) {
        List<ChatRoomMember> chatRoomMembers = new ArrayList<>();
        for(Member chatMember : chatMems){
            ChatRoomMember chatRoomMem = new ChatRoomMember(chatMember, chatRoom);
            chatRoomMembers.add(chatRoomMem);
        }

        List<ChatRoomMember> savedChatRoomMembers = chatRoomMemberRepository.saveAll(chatRoomMembers);
        List<ChatMemResponse> resultChatMem = savedChatRoomMembers.stream()
                .map(ChatMemResponse::from)
                .collect(Collectors.toList());

        return resultChatMem;
    }
}
