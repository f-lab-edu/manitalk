package com.example.web.exception.room;

/**
 * 채팅방을 삭제할 수 없는 경우
 */
public class CanNotDeleteRoomException extends RuntimeException {
    public CanNotDeleteRoomException(String message) {
        super(message);
    }

    public CanNotDeleteRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}