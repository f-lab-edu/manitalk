package com.example.web.exception.room;

/**
 * 채팅방 삭제에 실패한 경우
 */
public class FailDeleteRoomException extends RuntimeException {
    public FailDeleteRoomException(String message) {
        super(message);
    }

    public FailDeleteRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
