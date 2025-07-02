package com.yumyum.sns.post.service;


import com.yumyum.sns.attachment.dto.AttachwithDetailDto;
import com.yumyum.sns.attachment.dto.ThumbnailResponse;
import com.yumyum.sns.attachment.service.AttachmentService;
import com.yumyum.sns.error.exception.PostNotFoundException;
import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.service.MemberService;
import com.yumyum.sns.post.dto.*;
import com.yumyum.sns.post.entity.Post;
import com.yumyum.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final AttachmentService attachmentService;


    @Override
    public Post createPost(PostRequestDto postRequestDto,Member member, ThumbnailResponse createdAttach) {
        Post post = new Post(postRequestDto.getPostContent(), createdAttach.getThumbnailPath());
        post.setPostRelation(member,createdAttach.getAttachment());
        return postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public Post updatePost(Post updatedPost) {
        return postRepository.save(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDTO> getPagingPosts(Pageable pageable, Long memberId) {

        return postRepository.findPagingPosts(pageable,memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailDto getPostDetail(Long postId, Long memberId) {

        return Optional.ofNullable(postRepository.findPostDetail(postId, memberId))
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public MemberPostPageDto getMemberPosts(Pageable pageable, String nickName) {
        Member member = memberService.getMemberByNickname(nickName);
        List<MemberPostDto> memberPosts = postRepository.findMemberPosts(pageable, member.getId());
        return new MemberPostPageDto(memberPosts,pageable);
    }

    @Override
    public PostDetailDto getPostDetailWithInfo(Long memberId, Long postId) {
        PostDetailDto postDetail = getPostDetail(postId, memberId);
        List<AttachwithDetailDto> attachment = attachmentService.getAttachmentByPostDetail(postId);
        postDetail.setAttachments(attachment);
        return postDetail;
    }

}
