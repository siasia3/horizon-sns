package com.yumyum.sns.post.service;


import com.yumyum.sns.post.dto.LikeDto;
import com.yumyum.sns.post.entity.Likes;


public interface LikesService {

    // 좋아요 생성
    Long createLike(LikeDto likeDto,String identifier);

    // 좋아요 삭제
    void deleteLike(Long likeId);


}
