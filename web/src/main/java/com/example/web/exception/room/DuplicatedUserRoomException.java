package com.example.web.exception.room;

/**
 * 이미 해당 채팅방에 입장한 사용자인 경우
 */
public class DuplicatedUserRoomException extends RuntimeException {
    public DuplicatedUserRoomException(String message) {
        super(message);
    }

    public DuplicatedUserRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
