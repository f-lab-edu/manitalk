package com.example.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserParam {
    private String email;
}
