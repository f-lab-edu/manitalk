package com.example.web.exception.room;

/**
 * 메시지 전송에 실패한 경우
 */
public class FailSendMessageException extends RuntimeException {
    public FailSendMessageException(String message) {
        super(message);
    }

    public FailSendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
