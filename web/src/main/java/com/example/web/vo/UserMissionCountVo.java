package com.example.web.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserMissionCountVo {
    private Integer userId;
    private Integer messageCount;
}
