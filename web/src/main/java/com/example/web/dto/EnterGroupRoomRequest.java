package com.example.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class EnterGroupRoomRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer roomId;

    @NotBlank(message = "채팅방 이름은 반드시 입력해야 합니다.")
    @Size(max = 255, message = "채팅방 이름은 255자를 넘을 수 없습니다.")
    private String roomName;

    @NotBlank(message = "입장 코드는 반드시 입력해야 합니다.")
    @Size(max = 255, message = "입장 코드는 255자를 넘을 수 없습니다.")
    private String enterCode;

    @NotBlank(message = "닉네임은 반드시 입력해야 합니다.")
    @Size(max = 255, message = "닉네임은 255자를 넘을 수 없습니다.")
    private String nickname;
}
