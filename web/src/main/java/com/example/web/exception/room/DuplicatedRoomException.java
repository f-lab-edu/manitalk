package com.example.web.exception.room;

/**
 * 이미 채팅방이 존재하는 경우
 */
public class DuplicatedRoomException extends RuntimeException {
    public DuplicatedRoomException(String message) {
        super(message);
    }

    public DuplicatedRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
