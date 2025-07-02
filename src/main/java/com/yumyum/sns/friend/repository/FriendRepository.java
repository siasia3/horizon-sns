package com.yumyum.sns.friend.repository;

import com.yumyum.sns.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend,Long>,FriendRepositoryCustom {


}
