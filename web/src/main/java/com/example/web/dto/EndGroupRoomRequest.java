package com.example.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class EndGroupRoomRequest {

    @NotNull
    private Integer roomId;

    @NotNull
    private Integer roomOwnerId;
}
