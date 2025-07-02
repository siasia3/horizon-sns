package com.yumyum.sns.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {

    @NotNull
    private Long postId;
    @NotBlank
    private String commentContent;
    private Long parentId;

}
