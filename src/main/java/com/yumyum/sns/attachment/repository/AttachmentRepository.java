package com.yumyum.sns.attachment.repository;

import com.yumyum.sns.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment,Long>,AttachmentRepositoryCustom {
}
