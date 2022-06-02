package com.erisboxx.socialspace.exception;

import org.springframework.http.HttpStatus;

public class SocialspaceApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public SocialspaceApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public SocialspaceApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
