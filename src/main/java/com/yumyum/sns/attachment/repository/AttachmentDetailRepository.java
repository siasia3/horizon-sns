package com.yumyum.sns.attachment.repository;

import com.yumyum.sns.attachment.entity.Attachment;
import com.yumyum.sns.attachment.entity.AttachmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentDetailRepository extends JpaRepository<AttachmentDetail,Long> {
}
