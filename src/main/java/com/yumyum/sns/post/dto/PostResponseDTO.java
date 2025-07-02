package com.yumyum.sns.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yumyum.sns.attachment.dto.AttachDto;
import com.yumyum.sns.attachment.entity.Attachment;
import com.yumyum.sns.comment.entity.Comment;
import com.yumyum.sns.post.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDTO {


    private Long memberId;
    private Long postId;
    private Long attachmentId;
    private String profileImage;
    private String author;
    private String content;
    private LocalDateTime createdAt;
    private Long likeId;
    private Boolean likedByMember = false;
    private Long commentCount;
    private Long likeCount;
    private List<AttachDto> attachments;
    private List<TagDto> tagList;

    public PostResponseDTO(Long postId, Long memberId,String author,String profileImage, Long attachmentId, String content, LocalDateTime createdAt,Long likeCount,Long commentCount,Long likeId) {
        this.postId = postId;
        this.memberId = memberId;
        this.author = author;
        this.profileImage = profileImage;
        this.attachmentId = attachmentId;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.likeId = likeId;
        if(likeId != null && likeId > 0L){
            this.likedByMember = true;
        }
    }

   /*public PostResponseDTO(Long postId, Long memberId, String content, LocalDateTime createdAt, Long likeCount, Long commentCount, Long replyCount, Set<AttachDto> attachments, Set<TagDto> tagContents) {
        this.memberId = memberId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount+replyCount;
        this.attachments = attachments.stream().toList();
        this.tagList = tagContents.stream().toList();
    }*/
}
