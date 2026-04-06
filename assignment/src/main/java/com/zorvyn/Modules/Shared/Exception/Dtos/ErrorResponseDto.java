package com.zorvyn.Modules.Shared.Exception.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class ErrorResponseDto {
    private Integer StatusCode;
    private String msg;
    private Long timestamp;
}
