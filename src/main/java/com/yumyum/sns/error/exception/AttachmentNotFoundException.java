package com.yumyum.sns.error.exception;

public class AttachmentNotFoundException extends BaseNotFoundException{
    public AttachmentNotFoundException(Long postId) {
        super("해당 게시물에 대한 첨부파일을 찾을 수 없습니다: " + postId);
    }

}
