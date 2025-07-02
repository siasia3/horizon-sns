package com.yumyum.sns.comment.repository;

import com.yumyum.sns.attachment.dto.AttachDto;
import com.yumyum.sns.comment.dto.CommentCntDto;
import com.yumyum.sns.comment.dto.CommentDto;
import com.yumyum.sns.comment.dto.ReplyDto;
import com.yumyum.sns.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    //해당 게시글 목록들의 댓글 수 조회
    List<CommentCntDto> findCommentCntsByPostIds(List<Long> postIds);

    //해당 게시글의 댓글들 페이징 조회
    List<CommentDto> findCommentsByPost(Pageable pageable, Long postId);

    //댓글과 작성자 조회1
    Optional<Comment> findByIdWithMember(Long commentId);

    //대댓글 페이징 조회
    List<ReplyDto> findRepliesByComment(Pageable pageable, Long parentId);
}
