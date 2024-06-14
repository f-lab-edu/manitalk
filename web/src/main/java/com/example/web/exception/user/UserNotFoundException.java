package com.example.web.exception.user;

/**
 * 사용자를 찾지 못한 경우
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message);
    }
}
