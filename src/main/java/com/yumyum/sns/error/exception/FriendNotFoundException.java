package com.yumyum.sns.error.exception;

public class FriendNotFoundException extends BaseNotFoundException{

    public FriendNotFoundException(Long friendId) {
        super("해당하는 friend가 존재하지 않습니다: " + friendId);
    }
}
