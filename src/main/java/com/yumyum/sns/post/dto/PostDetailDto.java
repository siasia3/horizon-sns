package com.yumyum.sns.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yumyum.sns.attachment.dto.AttachwithDetailDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PostDetailDto {
    private Long memberId;
    private Long postId;
    private Long attachId;
    private String profileImage;
    private String author;
    private String content;
    private LocalDateTime createdAt;
    private Long likeId;
    private Boolean likedByMember = false;
    private Long likeCount;
    private List<AttachwithDetailDto> attachments;

    @QueryProjection
    public PostDetailDto(Long memberId, String profileImage, String author, Long postId, String content, LocalDateTime createdAt, Long attachId, Long likeCount,Long likeId) {
        this.memberId = memberId;
        this.postId = postId;
        this.attachId = attachId;
        this.profileImage = profileImage;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.likeId = likeId;
        if(likeId != null && likeId > 0L){
            this.likedByMember = true;
        }
    }
}
