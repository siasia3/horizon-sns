package com.yumyum.sns.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberPostPageDto {
    private List<MemberPostDto> content;
    private boolean hasNext = false;

    public MemberPostPageDto(List<MemberPostDto> content, Pageable pageable) {
        if (content.size() > pageable.getPageSize()) {
            content.remove(content.size() - 1);
            hasNext = true;
        }
        this.content = content;
    }
}
