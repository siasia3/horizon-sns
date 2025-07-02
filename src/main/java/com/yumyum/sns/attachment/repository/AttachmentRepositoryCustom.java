package com.yumyum.sns.attachment.repository;

import com.yumyum.sns.attachment.dto.AttachDto;
import com.yumyum.sns.attachment.dto.AttachwithDetailDto;

import java.util.List;

public interface AttachmentRepositoryCustom {

    //해당 게시글 목록들의 첨부파일 리스트를 조회
    List<AttachDto> findAttachmentsByPost(List<Long> attachIds);

    //해당 게시물의 첨부파일 조회
    List<AttachwithDetailDto> findAttachmentByPostDetail(Long postId);
}
