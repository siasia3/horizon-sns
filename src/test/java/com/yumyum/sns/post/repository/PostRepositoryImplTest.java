package com.yumyum.sns.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostRepositoryImplTest {


    @Autowired
    private PostRepository postRepository;


    @Test
    public void testFindPosts(){

        Pageable pageable = PageRequest.of(0, 5);
        postRepository.findPagingPosts(pageable,1L);
    }



}