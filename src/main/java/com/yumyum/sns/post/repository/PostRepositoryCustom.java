package com.yumyum.sns.post.repository;

import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.post.dto.MemberPostDto;
import com.yumyum.sns.post.dto.PostDetailDto;
import com.yumyum.sns.post.dto.PostResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostRepositoryCustom {

    //게시글 페이징조회
    List<PostResponseDTO> findPagingPosts(Pageable pageable, Long memberId);

    //게시글 상세조회
    PostDetailDto findPostDetail(Long postId, Long memberId);

    //회원 게시글 조회
    List<MemberPostDto> findMemberPosts(Pageable pageable, Long memberId);

    Long countTotalPosts();
}
