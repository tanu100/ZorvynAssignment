package com.zorvyn.Modules.Auth.DTOs;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class AuthResponseDto {

    private String jwtToken;
    private String name;
    private String email;
}

