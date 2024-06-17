package com.example.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CreateManitoRoomRequest {

    @NotNull
    private Integer groupRoomId;

    @NotNull(message = "만료일은 반드시 입력해야 합니다.")
    @Min(value = 1, message = "만료일은 최소 1일부터 지정 가능합니다.")
    @Max(value = 7, message = "만료일은 최대 7일까지 지정 가능합니다.")
    private Long expiresDays;
}
