package com.example.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ErrorMsg {
    private final String message;
    private final int httpStatusCode;

    public static ErrorMsg of(String message, HttpStatus httpStatus) {
        return new ErrorMsg(message, httpStatus.value());
    }
}
