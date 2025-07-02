package com.yumyum.sns.attachment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yumyum.sns.attachment.dto.AttachDto;
import com.yumyum.sns.attachment.dto.AttachwithDetailDto;
import com.yumyum.sns.attachment.dto.QAttachwithDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yumyum.sns.attachment.entity.QAttachment.attachment;
import static com.yumyum.sns.attachment.entity.QAttachmentDetail.attachmentDetail;
import static com.yumyum.sns.post.entity.QPost.*;

@Repository
@RequiredArgsConstructor
public class AttachmentRepositoryImpl implements AttachmentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AttachDto> findAttachmentsByPost(List<Long> attachIds) {

        List<AttachDto> attachList = queryFactory
                .select(Projections.constructor(AttachDto.class,
                        attachment.id,
                        attachmentDetail.id,
                        post.id,
                        attachmentDetail.path,
                        attachmentDetail.type
                ))
                .from(attachment)
                .join(attachment.attachments,attachmentDetail)
                .join(attachment.post, post)
                .where(attachment.id.in(attachIds))
                .fetch();

        return attachList;
    }

    @Override
    public List<AttachwithDetailDto> findAttachmentByPostDetail(Long postId) {
        List<AttachwithDetailDto> attachDetailList =  queryFactory
                .select(new QAttachwithDetailDto(
                        attachment.id,
                        attachmentDetail.id,
                        attachmentDetail.path,
                        attachmentDetail.type
                ))
                .from(attachment)
                .join(attachment.attachments, attachmentDetail)
                .where(attachment.post.id.eq(postId))
                .fetch();

        return attachDetailList;
    }

}
