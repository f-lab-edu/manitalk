package com.example.web.exception.room;

/**
 * 채팅방을 찾지 못한 경우
 */
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) {
        super(message);
    }

    public RoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
