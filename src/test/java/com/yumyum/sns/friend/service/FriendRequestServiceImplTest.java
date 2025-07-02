package com.yumyum.sns.friend.service;

import com.yumyum.sns.error.exception.FriendRequestNotFoundException;
import com.yumyum.sns.friend.dto.FriendRequestResDto;
import com.yumyum.sns.friend.dto.ReceivedFriendRequestDto;
import com.yumyum.sns.friend.repository.FriendRequestRepository;
import com.yumyum.sns.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class FriendRequestServiceImplTest {

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private FriendRequestRepository friendRequestRepository;


    @Test
    void getReceivedFriendReqs() {
        Long receiverId = 1L;
        List<ReceivedFriendRequestDto> receivedFriendReqs = friendRequestService.getReceivedFriendReqs(receiverId);
    }

    @Test
    void getSentFriendReqs() {
        Long senderId = 1L;
        friendRequestService.getSentFriendReqs(senderId);
    }

    @Test
    void deleteFriendReq() {
        Long friendRequestId = 1L;

        assertThrows(FriendRequestNotFoundException.class, ()->{
            friendRequestService.deleteFriendReq(friendRequestId);
        });
    }

    @Test
    void createFriendRequest() {
        FriendRequestResDto friendRequest = friendRequestService.createFriendRequest(1L, 2L);
        assertNotNull(friendRequest.getFriendRequestId());
    }

    @Test
    void getFriendRequestIdByMemberIds() {
    }
}