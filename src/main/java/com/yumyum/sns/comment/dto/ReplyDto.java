package com.yumyum.sns.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ReplyDto {
    private Long commentId;
    private String replyAuthor;
    private String replyContent;
    private Long memberId;
    private String authorProfileImage;
    private LocalDateTime createdAt;
    private Long parentId;

    @QueryProjection
    public ReplyDto(Long commentId, String replyAuthor, String replyContent, Long memberId, String authorProfileImage, LocalDateTime createdAt, Long parentId) {
        this.commentId = commentId;
        this.replyAuthor = replyAuthor;
        this.replyContent = replyContent;
        this.memberId = memberId;
        this.authorProfileImage = authorProfileImage;
        this.createdAt = createdAt;
        this.parentId = parentId;
    }
}
