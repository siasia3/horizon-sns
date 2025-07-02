package com.yumyum.sns.attachment.service;

import com.yumyum.sns.attachment.dto.AttachDto;
import com.yumyum.sns.attachment.dto.AttachwithDetailDto;
import com.yumyum.sns.attachment.dto.ThumbnailResponse;
import com.yumyum.sns.attachment.entity.Attachment;
import com.yumyum.sns.attachment.repository.AttachmentDetailRepository;
import com.yumyum.sns.attachment.repository.AttachmentRepository;
import com.yumyum.sns.error.exception.AttachmentNotFoundException;
import com.yumyum.sns.infra.s3.S3RollbackManager;
import com.yumyum.sns.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {



    private final S3RollbackManager s3RollbackManager;
    private final S3Service s3Service;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentDetailRepository attachmentDetailRepository;


    //Attachment와 AttachmentDetail insert
    @Override
    public ThumbnailResponse createAttachment(List<MultipartFile> files) {

        List<AttachDto> attachDtos = s3Service.uploadFiles(files);
        //롤백시 s3 정리
        s3RollbackManager.deleteIfTransactionRollback(s3Service.toSavedFileNames(attachDtos));

        Attachment attach = attachmentRepository.save(new Attachment());
        for (AttachDto attachDto : attachDtos) {
            attachmentDetailRepository.save(attachDto.toDto(attach));
        }

        return new ThumbnailResponse(attach, attachDtos.get(0).getPath());

    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<AttachDto>> getAttachmentsByPost(List<Long> attachIds) {
        List<AttachDto> attachList = attachmentRepository.findAttachmentsByPost(attachIds);
        //ID(postId키)와 DTO값(value)을 Map으로 변환
        Map<Long, List<AttachDto>> attachListMap = attachList.stream().collect(Collectors.groupingBy(attach -> attach.getPostId()));
        return attachListMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachwithDetailDto> getAttachmentByPostDetail(Long postId) {
        List<AttachwithDetailDto> attachment = attachmentRepository.findAttachmentByPostDetail(postId);
        if (attachment.isEmpty()) {
            throw new AttachmentNotFoundException(postId);
        }
        return attachment;
    }




}
