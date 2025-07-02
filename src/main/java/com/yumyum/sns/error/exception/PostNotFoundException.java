package com.yumyum.sns.error.exception;

public class PostNotFoundException extends BaseNotFoundException{

    public PostNotFoundException(Long postId) {
        super("해당 게시글을 찾을 수 없습니다: " + postId);
    }
}
