package com.yumyum.sns.error.exception;

public class CommentNotFoundException extends BaseNotFoundException{
    public CommentNotFoundException() {
        super("해당 부모댓글이 존재하지 않습니다.");
    }

    public CommentNotFoundException(Long commentId) {
        super("해당 댓글을 찾을 수 없습니다: " + commentId);
    }
    public CommentNotFoundException(String message) {
        super(message);
    }
}
