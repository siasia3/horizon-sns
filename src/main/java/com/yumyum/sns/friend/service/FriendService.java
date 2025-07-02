package com.yumyum.sns.friend.service;

import com.yumyum.sns.friend.dto.FriendReqDto;
import com.yumyum.sns.friend.dto.FriendResDto;
import com.yumyum.sns.friend.entity.Friend;
import com.yumyum.sns.friend.entity.FriendRequest;

import java.util.List;

public interface FriendService {

    //친구 엔티티 생성
    //친구 엔티티 삭제
    //친구 엔티티 목록 조회
    //친구 엔티티 단건 조회


    /** 친구 목록 조회
     * @param myId 로그인한 member id
     * @return 친구 목록
     */
    List<FriendResDto> getFriendsByMemberId(Long myId);

    /**
     * 친구 추가
     * @param myId  친구요청을 보낸 회원id
     * @param userId 친구요청을 수락한 회원id
     * @return friend ID
     */
    Long createFriend(Long myId, Long userId);

    /**
     * friendId를 이용한 친구 삭제
     * @param friendId friend id
     */
    void removeFriendByFriendId(Long friendId);

    /**
     * 친구관계인 회원Id를 이용한 친구 삭제
     * @param senderId 친구요청 보낸 회원id
     * @param receiverId 친구요청 수락한 회원id
     */
    void removeFriendByMemberId(Long senderId,Long receiverId);

    /**
     * 친구 단건 조회 없으면 예외발생
     * @param friendId 친구 id
     * @return Friend entity
     */
    Friend getFriendById(Long friendId);

}