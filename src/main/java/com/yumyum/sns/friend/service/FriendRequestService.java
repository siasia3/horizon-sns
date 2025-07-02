package com.yumyum.sns.friend.service;

import com.yumyum.sns.friend.dto.FriendRequestReqDto;
import com.yumyum.sns.friend.dto.FriendRequestResDto;
import com.yumyum.sns.friend.dto.ReceivedFriendRequestDto;
import com.yumyum.sns.friend.dto.SentFriendRequestDto;
import com.yumyum.sns.friend.entity.FriendRequest;

import java.util.List;
import java.util.Optional;

public interface FriendRequestService {


    /**
     * 본인이 받은 친구요청 조회
     * @param receiverId 친구요청을 받은 회원Id
     * @return 받은 친구요청 리스트
     */
    List<ReceivedFriendRequestDto> getReceivedFriendReqs(Long receiverId);

    /**
     * 본인이 보낸 친구요청 조회
     * @param senderId 친구요청을 보낸 회원Id
     * @return 보낸 친구요청 리스트
     */
    List<SentFriendRequestDto> getSentFriendReqs(Long senderId);


    /**
     * 친구요청 거절하는 경우 또는 본인이 취소한 경우 삭제
     * @param friendRequestId 친구요청 id
     */
    void deleteFriendReq(Long friendRequestId);

    /**
     * 친구삭제하는 경우, 요청삭제(사용X)
     * @param myId 본인 회원id
     * @param userId 친구인 회원id
     */
    void removeFriendRequestOnFriendRemoval(Long myId, Long userId);

    /**
     * 친구요청 엔티티 insert
     * @param senderId 친구요청 보낸 회원id
     * @param receiverId 친구요청 받은 회원id
     * @return FriendRequestResDto 친구요청 정보
     */
    FriendRequestResDto createFriendRequest(Long senderId,Long receiverId);

    /**
     * id 값을 통해 조회하고 없을시 exception
     * @param friendRequestId 회원요청 id
     * @return 조회된 FriendRequest
     */
    FriendRequest getFriendRequestById(Long friendRequestId);

    /**
     * 친구요청을 받거나 보낸 상태인지 확인
     * @param myId 본인 회원id
     * @param userId 친구요청 확인 회원id
     * @return 친구요청 dto
     */
    Optional<FriendRequestResDto> getFriendRequestIdByMemberIds(Long myId, Long userId);

    /**
     * 친구요청 수락시 state 변경(update)
     * @param friendRequestId 친구요청 id
     * @return 친구요청 정보
     */
    FriendRequestResDto acceptFriendRequest(Long friendRequestId);

}
