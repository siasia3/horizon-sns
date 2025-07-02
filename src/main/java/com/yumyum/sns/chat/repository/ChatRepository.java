package com.yumyum.sns.chat.repository;

import com.yumyum.sns.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>,ChatRepositoryCustom {
}
