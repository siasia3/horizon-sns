package com.yumyum.sns.post.repository;

import com.yumyum.sns.post.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag,Long> {
}
