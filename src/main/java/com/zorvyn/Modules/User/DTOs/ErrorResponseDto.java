package com.zorvyn.Modules.User.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorResponseDto {
    private Integer StatusCode;
    private String msg;
    private Long timestamp;
}
