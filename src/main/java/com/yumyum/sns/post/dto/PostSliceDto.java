package com.yumyum.sns.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostSliceDto {
        private List<PostResponseDTO> content;
        private boolean hasNext = false;

    public PostSliceDto(List<PostResponseDTO> content, Pageable pageable) {
        //다음페이지 존재유무 체크
        if (content.size() > pageable.getPageSize()) {
            content.remove(content.size() - 1);
            hasNext = true;
        }
        this.content = content;
    }
}
