package com.yumyum.sns.friend.repository;

import com.yumyum.sns.friend.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>,FriendRequestRepositoryCustom {
}
