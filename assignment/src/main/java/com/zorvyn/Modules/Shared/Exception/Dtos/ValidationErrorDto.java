package com.zorvyn.Modules.Shared.Exception.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Builder
@Getter
@Setter
public class ValidationErrorDto {
    private Integer StatusCode;
    private Long timestamp;
    private HashMap<String,String> map;
}
