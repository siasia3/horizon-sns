package com.yumyum.sns.friend.service;

import com.yumyum.sns.error.exception.custom.BusinessException;
import com.yumyum.sns.error.exception.errorcode.ErrorCode;
import com.yumyum.sns.friend.dto.FriendRequestResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class FriendManagementService {

    private final FriendService friendService;
    private final FriendRequestService friendRequestService;

    //친구 요청 수락
    public FriendRequestResDto acceptFriend(Long friendRequestId){

        //friendRequest update
        FriendRequestResDto friendRequestResDto = friendRequestService.acceptFriendRequest(friendRequestId);
        //friend insert
        Long friendId = friendService.createFriend(friendRequestResDto.getSenderId(), friendRequestResDto.getReceiverId());

        return friendRequestResDto;
    }

    // 친구 Id를 통한 친구 삭제
    public void unFriendByFriendId(Long friendId,Long myId,Long memberId){
        FriendRequestResDto friendRequestResDto = friendRequestService.getFriendRequestIdByMemberIds(myId, memberId).orElseThrow(() -> new BusinessException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));
        friendRequestService.deleteFriendReq(friendRequestResDto.getFriendRequestId());
        friendService.removeFriendByFriendId(friendId);
    }
    // 회원 Id를 통한 친구삭제
    public void unFriendByMemberId(Long myId,Long memberId){
        FriendRequestResDto friendRequestResDto = friendRequestService.getFriendRequestIdByMemberIds(myId, memberId).orElseThrow(() -> new BusinessException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));
        friendRequestService.deleteFriendReq(friendRequestResDto.getFriendRequestId());
        friendService.removeFriendByMemberId(friendRequestResDto.getSenderId(),friendRequestResDto.getReceiverId());
    }

}