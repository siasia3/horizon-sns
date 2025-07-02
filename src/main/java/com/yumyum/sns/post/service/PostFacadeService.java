package com.yumyum.sns.post.service;

import com.yumyum.sns.post.dto.PostDetailDto;
import com.yumyum.sns.post.dto.PostRequestDto;
import com.yumyum.sns.post.dto.PostSliceDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostFacadeService {

    //facade패턴으로 파일생성,게시글생성,해시태그생성 서비스를 호출
    void createPost(PostRequestDto postRequestDto, List<MultipartFile> files, String identifier);

    //게시글 과 게시글 관련 정보 페이징 조회
    PostSliceDto getPostsWithInfo(Pageable pageable, Long memberId);

}
