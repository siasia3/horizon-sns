package com.yumyum.sns.attachment.dto;

import com.yumyum.sns.attachment.entity.Attachment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ThumbnailResponse {
    private Attachment attachment;
    private String thumbnailPath;

    public ThumbnailResponse(Attachment attachment, String thumbnailPath) {
        this.attachment = attachment;
        this.thumbnailPath = thumbnailPath;
    }
}
