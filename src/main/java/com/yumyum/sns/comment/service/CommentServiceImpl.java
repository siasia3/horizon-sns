package com.yumyum.sns.comment.service;

import com.yumyum.sns.comment.dto.*;
import com.yumyum.sns.comment.entity.Comment;
import com.yumyum.sns.comment.repository.CommentRepository;
import com.yumyum.sns.error.exception.custom.BusinessException;
import com.yumyum.sns.error.exception.errorcode.ErrorCode;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.service.MemberService;
import com.yumyum.sns.notification.NotificationType;
import com.yumyum.sns.notification.TargetType;
import com.yumyum.sns.notification.event.NotificationEvent;
import com.yumyum.sns.post.entity.Post;
import com.yumyum.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{

    private final MemberService memberService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;


    //댓글 등록
    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String identifier) {
        Member member = memberService.getMemberByIdentifier(identifier);
        Post post = postService.getPostById(commentRequestDto.getPostId());
        String commentContent = commentRequestDto.getCommentContent();
        Comment comment = new Comment(post,member, commentContent);
        Long parentId = commentRequestDto.getParentId();
        Comment savedComment = commentRepository.save(comment);

        log.info("게시글 작성자 ID={}, 댓글 작성자 ID={},부모 댓글 ID={}",
                post.getMember().getId(),
                member.getId(),
                parentId);

        if(parentId != null){
            Comment parentComment = commentRepository.findById(commentRequestDto.getParentId()).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
            comment.setParentId(parentComment);

            //대댓글 알림
            if (!parentComment.getMember().getId().equals(member.getId())) {
                eventPublisher.publishEvent(new NotificationEvent(
                        parentComment.getMember().getId(),
                        member.getId(),
                        NotificationType.REPLY,
                        TargetType.COMMENT,
                        parentComment.getId()
                ));
            }
        }else{
            //댓글 알림
            if (!post.getMember().getId().equals(member.getId())) {
                log.info("=== 이벤트 발행 ===");
                eventPublisher.publishEvent(new NotificationEvent(
                        post.getMember().getId(),
                        member.getId(),
                        NotificationType.COMMENT,
                        TargetType.POST,
                        post.getId()
                ));
            }
        }

        return new CommentResponseDto(member, savedComment);
    }

    //게시글 리스트의 각 댓글개수
    @Override
    @Transactional(readOnly = true)
    public Map<Long, CommentCntDto> getCommentCntsByPostIds(List<Long> postIds) {
        List<CommentCntDto> totalCommentCnts = commentRepository.findCommentCntsByPostIds(postIds);
        Map<Long, CommentCntDto> totalCommentCntMap = totalCommentCnts.stream().collect(Collectors.toMap(CommentCntDto::getPostId, comment -> comment));
        return totalCommentCntMap;
    }

    //특정 게시글의 댓글조회
    @Override
    @Transactional(readOnly = true)
    public CommentSliceDto getCommentsByPost(Pageable pageable, Long postId) {
        List<CommentDto> commentsByPost = commentRepository.findCommentsByPost(pageable, postId);
        return new CommentSliceDto(commentsByPost,pageable);
    }

    //댓글 삭제
    @Override
    @Transactional
    public Long deleteComment(Long commentId, String identifier) {
        Comment comment = commentRepository.findByIdWithMember(commentId).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
        Member member = memberService.getMemberByIdentifier(identifier);
        if(comment.getMember().getId() != member.getId()){
            throw new BusinessException(ErrorCode.COMMENT_DELETE_FORBIDDEN);
        }
        commentRepository.delete(comment);
        return comment.getId();
    }

    //대댓글 조회
    @Override
    @Transactional(readOnly = true)
    public ReplySliceDto getRepliesByComment(Pageable pageable, Long parentId) {
        List<ReplyDto> replies = commentRepository.findRepliesByComment(pageable, parentId);

        if (replies.isEmpty()) {
            throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
        }
        return new ReplySliceDto(replies,pageable);
    }

}