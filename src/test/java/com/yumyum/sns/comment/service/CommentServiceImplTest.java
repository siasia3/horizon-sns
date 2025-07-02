package com.yumyum.sns.comment.service;

import com.yumyum.sns.comment.dto.CommentSliceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void testFindComments(){

        Pageable pageable = PageRequest.of(0, 10);
        CommentSliceDto commentsByPost = commentService.getCommentsByPost(pageable, 1L);
        System.out.println("commentsByPost = " + commentsByPost);
    }



}