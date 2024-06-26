package com.example.web.exception.room;

/**
 * 메시지를 전송할 수 없는 경우
 */
public class CanNotSendMessageException extends RuntimeException {
    public CanNotSendMessageException(String message) {
        super(message);
    }

    public CanNotSendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
