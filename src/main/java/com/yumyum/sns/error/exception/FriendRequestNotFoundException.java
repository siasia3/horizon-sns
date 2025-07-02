package com.yumyum.sns.error.exception;

public class FriendRequestNotFoundException extends BaseNotFoundException{
    public FriendRequestNotFoundException(Long friendRequestId) {
        super("해당 키를 가진 엔티티가 존재하지 않습니다: "+friendRequestId);
    }

    public FriendRequestNotFoundException(Long myId,Long userId) {
        super("해당 회원들에게 조회된 친구요청 엔티티가 없습니다. 회원A: " + myId + " 회원B: " + userId);
    }


}
