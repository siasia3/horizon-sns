package com.yumyum.sns.post.dto;

import com.yumyum.sns.post.entity.Post;
import com.yumyum.sns.post.entity.Tag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TagDto {
    private Long postId;
    private Long tagId;
    private String content;


    public TagDto(Long tagId,Long postId, String content) {
        this.tagId = tagId;
        this.postId = postId;
        this.content = content;
    }

}
