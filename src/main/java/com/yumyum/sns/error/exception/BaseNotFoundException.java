package com.yumyum.sns.error.exception;

public class BaseNotFoundException extends RuntimeException {
    public BaseNotFoundException(String message) {
        super(message);
    }
}