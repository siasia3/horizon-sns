package com.yumyum.sns.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CommentDto {
    private Long commentId;
    private String commentAuthor;
    private String commentContent;
    private Long memberId;
    private String authorProfileImage;
    private LocalDateTime createdAt;
    private Long replyCount;

    @QueryProjection
    public CommentDto(Long commentId, String commentContent, LocalDateTime createdAt, Long memberId, String commentAuthor, String authorProfileImage, Long replyCount) {
        this.commentId = commentId;
        this.commentAuthor = commentAuthor;
        this.commentContent = commentContent;
        this.memberId = memberId;
        this.authorProfileImage = authorProfileImage;
        this.createdAt = createdAt;
        this.replyCount = replyCount;
    }


}
