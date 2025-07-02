package com.yumyum.sns.chat.service;

import com.yumyum.sns.chat.dto.ChatMemResponse;
import com.yumyum.sns.chat.dto.DirectChatMemRequest;
import com.yumyum.sns.chat.entity.ChatRoom;
import com.yumyum.sns.member.entity.Member;

import java.util.List;

public interface ChatRoomMemberService {

    /**
     * 채팅방 멤버 생성
     * @param chatMems 채팅방 회원들 리스트
     * @param chatRoom 채팅방 엔티티
     * @return 생성된 채팅방 회원 id
     */
    List<ChatMemResponse> createChatRoomMem(List<Member> chatMems, ChatRoom chatRoom);
}
