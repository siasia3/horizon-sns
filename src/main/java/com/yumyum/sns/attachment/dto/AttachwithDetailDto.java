package com.yumyum.sns.attachment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class AttachwithDetailDto {
    private Long attachmentId;
    private Long attachDetailId;
    private String path;
    private String type;

    @QueryProjection
    public AttachwithDetailDto(Long attachmentId, Long attachDetailId, String path, String type) {
        this.attachmentId = attachmentId;
        this.attachDetailId = attachDetailId;
        this.path = path;
        this.type = type;
    }
}
