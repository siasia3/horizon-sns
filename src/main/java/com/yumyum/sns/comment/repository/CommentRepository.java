package com.yumyum.sns.comment.repository;

import com.yumyum.sns.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>,CommentRepositoryCustom {
}
