package com.yumyum.sns.attachment.entity;

import com.yumyum.sns.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "attachment")
    private List<AttachmentDetail> attachments = new ArrayList<>();

    @OneToOne(mappedBy = "attachment", fetch = FetchType.LAZY)
    private Post post;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // 현재 시간을 설정
    }

}
