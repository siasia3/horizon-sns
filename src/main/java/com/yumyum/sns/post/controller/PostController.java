package com.yumyum.sns.post.controller;

import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.service.MemberService;
import com.yumyum.sns.oauthjwt.jwt.JWTUtil;
import com.yumyum.sns.post.dto.*;
import com.yumyum.sns.post.service.PostFacadeServiceImpl;
import com.yumyum.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostFacadeServiceImpl postFacadeService;
    private final PostService postService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping(value = "/post", consumes = "multipart/form-data")
    public ResponseEntity<Map<String,String>> createPost(@CookieValue(name = "Authorization") String jwt,
                                        @RequestPart(value= "postContent", required = false) PostRequestDto postRequestDto,
                                        @RequestPart(value = "files") List<MultipartFile> files){

        boolean hasFiles = files != null && !files.isEmpty();
        if(!hasFiles){
            return ResponseEntity.badRequest().body(Map.of("message","파일은 반드시 포함해야 합니다."));
        }
        boolean hasInvalid = files.stream()
                .map(MultipartFile::getContentType)
                .anyMatch(type -> !type.startsWith("image/") && !type.startsWith("video/"));

        if (hasInvalid) {
            return ResponseEntity.badRequest().body(Map.of("message","이미지나 동영상 파일이 아닙니다."));
        }

        String identifier = jwtUtil.getUsername(jwt);
        postFacadeService.createPost(postRequestDto,files,identifier);

        return ResponseEntity.ok(Map.of("message","게시글이 정상적으로 작성되었습니다."));
    }

    @GetMapping(value = "/posts")
    public PostSliceDto getPosts(Pageable pageable,
                                 @CookieValue(name = "Authorization") String jwt){
        String username = jwtUtil.getUsername(jwt);
        Member member = memberService.getMemberByIdentifier(username);
        return postFacadeService.getPostsWithInfo(pageable,member.getId());
    }

    @GetMapping(value = "/post/{postId}")
    public PostDetailDto getPostDetail(@CookieValue(name = "Authorization") String jwt,
                                       @PathVariable Long postId){
        String username = jwtUtil.getUsername(jwt);
        Member member = memberService.getMemberByIdentifier(username);
        return postService.getPostDetailWithInfo(member.getId(),postId);
    }

    @GetMapping(value="/user/{nickname}/posts")
    public ResponseEntity<MemberPostPageDto> getMemberPosts(Pageable pageable,
                                                              @PathVariable String nickname){
        MemberPostPageDto memberPosts = postService.getMemberPosts(pageable, nickname);
        return ResponseEntity.ok(memberPosts);
    }



}
