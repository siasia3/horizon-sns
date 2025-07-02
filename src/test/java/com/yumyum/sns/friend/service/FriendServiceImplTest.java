package com.yumyum.sns.friend.service;

import com.yumyum.sns.friend.dto.FriendResDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FriendServiceImplTest {

    @Autowired
    private FriendService friendService;

    @Test
    void getFriendsTest(){
        List<FriendResDto> friendsByMemberId = friendService.getFriendsByMemberId(1L);
        System.out.println("friendsByMemberId.size() = " + friendsByMemberId.size());
    }


}