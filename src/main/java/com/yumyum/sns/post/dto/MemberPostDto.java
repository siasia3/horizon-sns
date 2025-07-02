package com.yumyum.sns.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yumyum.sns.attachment.dto.AttachDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberPostDto {
    private Long postId;
    private String thumbnailPath;
    private Long likeCount;
    private Long commentCount;

    @QueryProjection
    public MemberPostDto(Long postId, String thumbnailPath, Long likeCount, Long commentCount) {
        this.postId = postId;
        this.thumbnailPath = thumbnailPath;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
