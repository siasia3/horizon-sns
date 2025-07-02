package com.yumyum.sns.error.exception;

import lombok.RequiredArgsConstructor;

public class FileUploadException extends RuntimeException{

    public FileUploadException(String message) {
        super(message);
    }
}
