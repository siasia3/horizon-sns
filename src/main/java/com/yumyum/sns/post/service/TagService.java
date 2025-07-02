package com.yumyum.sns.post.service;

import com.yumyum.sns.post.dto.PostRequestDto;
import com.yumyum.sns.post.dto.TagDto;
import com.yumyum.sns.post.entity.Post;
import com.yumyum.sns.post.entity.PostTag;
import com.yumyum.sns.post.entity.Tag;

import java.util.List;

public interface TagService {

    //해시태그와 게시글해시태그 중간테이블 생성
    PostTag createTag(TagDto tagDto, Post post);

    //게시판 해시태그 조회
    List<Tag> getTags(Long postId);

    //해시태그 삭제
    void deleteTag(Long tagId);
}
