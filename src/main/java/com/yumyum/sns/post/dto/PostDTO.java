package com.yumyum.sns.post.dto;

import com.yumyum.sns.attachment.dto.AttachDto;
import com.yumyum.sns.attachment.dto.AttachwithDetailDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostBeforeUpdateDTO {

    private Long postId;
    private Long memberId;
    private Long attachId;
    private String postContent;
    private LocalDateTime createdAt;
    private String thumbnailPath;
    private List<AttachwithDetailDto> attachments;

    public PostBeforeUpdateDTO(Long postId, String postContent, List<AttachwithDetailDto> attachments) {
        this.postId = postId;
        this.postContent = postContent;
        this.attachments = attachments;
    }
}
