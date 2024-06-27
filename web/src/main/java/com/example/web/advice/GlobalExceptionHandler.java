package com.example.web.advice;

import com.example.web.exception.ErrorMsg;
import com.example.web.exception.room.CanNotEnterRoomException;
import com.example.web.exception.room.CanNotSendMessageException;
import com.example.web.exception.room.DuplicatedRoomException;
import com.example.web.exception.room.DuplicatedUserRoomException;
import com.example.web.exception.room.RoomNotFoundException;
import com.example.web.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorMsg> createResponse(String message, HttpStatus httpStatus) {
        ErrorMsg errorMsg = ErrorMsg.of(message, httpStatus);
        return new ResponseEntity<>(errorMsg, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMsg> validationNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : e.getMessage();
        return createResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMsg> userNotFoundException(UserNotFoundException e) {
        return createResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorMsg> roomNotFoundException(RoomNotFoundException e) {
        return createResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CanNotEnterRoomException.class, CanNotSendMessageException.class})
    public ResponseEntity<ErrorMsg> canNotEnterRoomException(RuntimeException e) {
        return createResponse(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({DuplicatedUserRoomException.class, DuplicatedRoomException.class})
    public ResponseEntity<ErrorMsg> duplicatedUserRoomException(RuntimeException e) {
        return createResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
