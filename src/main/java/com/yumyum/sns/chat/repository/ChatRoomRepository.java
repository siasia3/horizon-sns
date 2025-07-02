package com.yumyum.sns.chat.repository;

import com.yumyum.sns.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long>,ChatRoomRepositoryCustom{
}
