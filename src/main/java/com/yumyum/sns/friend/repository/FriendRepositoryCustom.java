package com.yumyum.sns.friend.repository;

import com.yumyum.sns.friend.dto.FriendResDto;

import java.util.List;

public interface FriendRepositoryCustom {

    //getFriendsBymemberId;

    /**
     * 친구 목록을 가져온다.
     * @param myId 현재 로그인한 회원id
     * @return 친구 리스트
     */
    public List<FriendResDto> findFriendsBymemberId(Long myId);

    /**
     * 친구 삭제
     * @param senderId 친구요청 보낸사람 ID
     * @param receiverId 친구요청 수락한사람 ID
     */
    public void deleteFriend(Long senderId,Long receiverId);







}
