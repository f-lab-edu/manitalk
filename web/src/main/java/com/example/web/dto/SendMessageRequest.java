package com.example.web.dto;

import com.example.web.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SendMessageRequest {

    @NotNull
    private Integer roomId;

    @NotNull
    private Integer userId;

    @NotNull
    private MessageType messageType;

    @NotBlank(message = "전송할 메시지 내용을 입력하세요.")
    private String content;
}
