package com.example.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CreateUserRoomsParam {
    List<Integer> roomIds;
    private Map<Integer, Integer> pairs;
}
