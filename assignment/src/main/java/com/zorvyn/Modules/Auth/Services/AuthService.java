package com.zorvyn.Modules.Auth.Services;

import com.zorvyn.Modules.Auth.DTOs.AuthResponseDto;
import com.zorvyn.Modules.Auth.DTOs.LoginRequestDto;
import com.zorvyn.Modules.Auth.DTOs.SignupRequestDto;
import com.zorvyn.Modules.Security.Jwt.JwtUtil;
import com.zorvyn.Modules.Shared.Exception.Errors.UserNotFoundException;
import com.zorvyn.Modules.Shared.Enums.Role;
import com.zorvyn.Modules.Shared.Enums.Status;
import com.zorvyn.Modules.User.Models.User;
import com.zorvyn.Modules.User.Repository.JpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JpaRepo table;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthResponseDto signup(SignupRequestDto signupRequestDto){
        User user = new User();
        user.setName(signupRequestDto.getName());
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setRole(Role.VIEWER);
        user.setStatus(Status.ACTIVE);

        User savedUser = table.save(user);

        return AuthResponseDto.builder().jwtToken(jwtUtil.generateToken(signupRequestDto.getEmail(), String.valueOf(Role.VIEWER)))
                .email(signupRequestDto.getEmail())
                .name(signupRequestDto.getName()).build();
    }

    public AuthResponseDto login(LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        User user=table.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->new UserNotFoundException("User Not Found"));
        String jwtToken=jwtUtil.generateToken(user.getEmail(),user.getRole().name());
        return AuthResponseDto.builder().jwtToken(jwtToken).email(user.getEmail()).name(user.getName()).build();
    }

}
