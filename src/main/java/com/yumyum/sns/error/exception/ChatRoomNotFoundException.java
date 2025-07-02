package com.yumyum.sns.error.exception;

public class ChatRoomNotFoundException extends BaseNotFoundException{

    public ChatRoomNotFoundException(Long chatRoomId) {
        super("해당 채팅방은 조회되지 않는 채팅방입니다. : " + chatRoomId);
    }
}
