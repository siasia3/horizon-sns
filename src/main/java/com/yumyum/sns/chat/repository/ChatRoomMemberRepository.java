package com.yumyum.sns.chat.repository;

import com.yumyum.sns.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember,Long> {
}
