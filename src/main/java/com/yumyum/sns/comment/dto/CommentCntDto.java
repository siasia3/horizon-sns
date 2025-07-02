package com.yumyum.sns.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCntDto {
    private Long postId;
    private Long totalCommentCnt;

    public CommentCntDto(Long postId, Long totalCommentCnt) {
        this.postId = postId;
        this.totalCommentCnt = totalCommentCnt;
    }
}
