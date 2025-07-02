package com.yumyum.sns.chat.repository;

import com.yumyum.sns.chat.dto.DirectChatMemRequest;
import com.yumyum.sns.chat.entity.ChatRoom;
import com.yumyum.sns.chat.entity.ChatRoomMember;
import com.yumyum.sns.chat.entity.ChatRoomType;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatRoomRepositoryImplTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void dummyChatRoom(){
        ChatRoom chatRoom = new ChatRoom(ChatRoomType.ONETOONE);
        ChatRoom chatRoom2 = new ChatRoom(ChatRoomType.GROUP);
        Optional<Member> member1 = memberRepository.findById(1L);
        Optional<Member> member2 = memberRepository.findById(2L);

        chatRoomRepository.save(chatRoom);
        chatRoomRepository.save(chatRoom2);

        ChatRoomMember chatRoomMember1 = new ChatRoomMember(member1.get(), chatRoom);
        ChatRoomMember chatRoomMember2 = new ChatRoomMember(member2.get(), chatRoom);

        chatRoomMemberRepository.save(chatRoomMember1);
        chatRoomMemberRepository.save(chatRoomMember2);

        ChatRoomMember chatRoomMember3 = new ChatRoomMember(member1.get(), chatRoom2);
        ChatRoomMember chatRoomMember4 = new ChatRoomMember(member2.get(), chatRoom2);

        chatRoomMemberRepository.save(chatRoomMember3);
        chatRoomMemberRepository.save(chatRoomMember4);

        DirectChatMemRequest directChatMemRequest = new DirectChatMemRequest(1L, 2L);
        Optional<ChatRoom> chatRoomInfo = chatRoomRepository.findChatRoom(directChatMemRequest);
        assertThat(chatRoomInfo.get().getId()).isEqualTo(1L);
    }

}