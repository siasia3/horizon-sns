package com.yumyum.sns.error.exception;

public class MemberNotFoundException extends BaseNotFoundException{

    public MemberNotFoundException() {
        super("회원이 존재하지 않습니다.");
    }

    public MemberNotFoundException(String identifier) {
        super("해당 회원을 찾을 수 없습니다: " + identifier);
    }
}
