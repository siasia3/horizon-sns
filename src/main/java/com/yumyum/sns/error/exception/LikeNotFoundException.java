package com.yumyum.sns.error.exception;

public class LikeNotFoundException  extends BaseNotFoundException{
    public LikeNotFoundException(Long likeId) {
        super("해당 좋아요를 찾을 수 없습니다: " + likeId);
    }
}
