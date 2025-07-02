package com.yumyum.sns.friend.repository;

import com.yumyum.sns.friend.dto.ReceivedFriendRequestDto;
import com.yumyum.sns.friend.dto.SentFriendRequestDto;
import com.yumyum.sns.friend.entity.FriendRequest;
import com.yumyum.sns.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepositoryCustom {

    /**
     * 본인이 받은 친구요청 조회
     * @param receiverId 친구요청을 받은 회원id
     * @return 받은 친구요청 dto 리스트
     */
    public List<ReceivedFriendRequestDto> findFriendRequestsByReceiver(Long receiverId);

    /**
     * 본인이 보낸 친구요청 조회
     * @param senderId 친구요청을 보낸 회원id
     * @return 보낸 친구요청 dto 리스트
     */
    public List<SentFriendRequestDto> findFriendRequestsBySender(Long senderId);

    /**
     * 특정회원과의 친구요청이 존재하는지 확인
     * @param myId 회원 본인 id
     * @param memberId 특정 회원 id
     * @return 친구요청 엔티티
     */
    public Optional<FriendRequest> findFriendRequestIdByMemberIds(Long myId, Long memberId);


    /**
     * 본인과 친구 id를 이용한 친구요청 삭제
     * @param myId 본인 회원id
     * @param memberId 친구인 회원id
     */
    public void deleteFriendRequestOnFriendRemoval(Long myId, Long memberId);
}
