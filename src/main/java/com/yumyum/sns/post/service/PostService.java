package com.yumyum.sns.post.service;

import com.yumyum.sns.attachment.dto.ThumbnailResponse;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.post.dto.*;
import com.yumyum.sns.post.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    // 게시물 생성
    Post createPost(PostRequestDto postRequestDto, Member member, ThumbnailResponse attachment);

    // 게시물 단건 조회
    Post getPostById(Long postId);

    // 게시물 수정
    Post updatePost(Post updatedPost);

    // 게시물 삭제
    void deletePost(Long postId);

    // 게시글 페이징 조회
    List<PostResponseDTO> getPagingPosts(Pageable pageable, Long memberId);

    //게시글 상세조회
    PostDetailDto getPostDetail(Long postId, Long memberId);

    //회원 게시글 조회
    MemberPostPageDto getMemberPosts(Pageable pageable, String nickName);

    //게시글,게시글 파일 상세조회
    PostDetailDto getPostDetailWithInfo(Long memberId,Long postId);

}
