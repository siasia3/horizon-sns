package com.yumyum.sns.post.repository;

import com.yumyum.sns.post.dto.PostResponseDTO;
import com.yumyum.sns.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>,PostRepositoryCustom {

}
