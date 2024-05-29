package com.example.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CreateGroupRoomDto {

    @NotNull
    private Integer roomOwnerId;

    @NotBlank
    @Size(max = 255, message = "채팅방 이름은 255자를 넘을 수 없습니다.")
    private String roomName;

    @NotBlank
    @Size(max = 255, message = "채팅장 입장 코드는 255자를 넘을 수 없습니다.")
    private String enterCode;
}
