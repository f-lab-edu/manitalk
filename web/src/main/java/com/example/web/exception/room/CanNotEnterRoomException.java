package com.example.web.exception.room;

/**
 * 채팅방에 입장할 수 없는 경우
 * - 입장코드 틀림
 * - 채팅방의 멤버가 아님
 */
public class CanNotEnterRoomException extends RuntimeException {
    public CanNotEnterRoomException(String message) {
        super(message);
    }

    public CanNotEnterRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
