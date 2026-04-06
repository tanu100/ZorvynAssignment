package com.zorvyn.Modules.Auth.Controller;

import com.zorvyn.Modules.Auth.DTOs.AuthResponseDto;
import com.zorvyn.Modules.Auth.DTOs.LoginRequestDto;
import com.zorvyn.Modules.Auth.DTOs.SignupRequestDto;
import com.zorvyn.Modules.Auth.Services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService  authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody @Valid SignupRequestDto signRequestDto){
        return new ResponseEntity<>(authService.signup(signRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }
}
