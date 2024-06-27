package com.example.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateManitoRoomDetailsParam {
    private Integer groupRoomId;
    List<Integer> roomIds;
    private Long expiresDays;
}
