package com.yumyum.sns.comment.service;

import com.yumyum.sns.attachment.entity.Attachment;
import com.yumyum.sns.comment.dto.*;
import com.yumyum.sns.comment.entity.Comment;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.service.MemberService;
import com.yumyum.sns.post.dto.PostRequestDto;
import com.yumyum.sns.post.dto.PostSliceDto;
import com.yumyum.sns.post.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CommentService {

    // 댓글 작성
    CommentResponseDto createComment(CommentRequestDto commentRequestDto, String identifier);

    //해당 게시글 목록들의 댓글 수 조회
    Map<Long, CommentCntDto> getCommentCntsByPostIds(List<Long> postIds);

    //해당 게시글의 댓글들 조회
    CommentSliceDto getCommentsByPost(Pageable pageable, Long postId);

    //댓글 삭제
    Long deleteComment(Long commentId, String identifier);

    //대댓글 페이징 조회
    ReplySliceDto getRepliesByComment(Pageable pageable, Long parentId);

}
