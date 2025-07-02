package com.yumyum.sns.attachment.service;

import com.yumyum.sns.attachment.dto.AttachDto;
import com.yumyum.sns.attachment.dto.AttachwithDetailDto;
import com.yumyum.sns.attachment.dto.ThumbnailResponse;
import com.yumyum.sns.attachment.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AttachmentService {

    // 파일 생성
    ThumbnailResponse createAttachment(List<MultipartFile> files);

    //해당 게시글 목록의 첨부파일 리스트를 조회
    Map<Long, List<AttachDto>> getAttachmentsByPost(List<Long> attachIds);

    //특정 게시물의 첨부파일 조회
    List<AttachwithDetailDto> getAttachmentByPostDetail(Long postId);

}
