package com.yumyum.sns.attachment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttachmentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATTACHMENT_ID")
    private Attachment attachment;

    private String originalFileName;
    private String savedFileName;
    private String path;
    private Long size;
    private String type;

    public AttachmentDetail(Long size, String fileName, String fileUrl, String fileKey, String mimeType) {
        this.size = size;
        this.originalFileName = fileName;
        this.path = fileUrl;
        this.savedFileName = fileKey;
        this.type = mimeType;
    }

}
