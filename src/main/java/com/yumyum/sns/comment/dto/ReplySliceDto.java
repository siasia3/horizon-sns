package com.yumyum.sns.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ReplySliceDto {
    private List<ReplyDto> content;
    private boolean hasNext = false;

    public ReplySliceDto(List<ReplyDto> content, Pageable pageable) {
        this.content = content;
        if (content.size() > pageable.getPageSize()) {
            content.remove(content.size() - 1);
            hasNext = true;
        }
    }
}
