package com.zorvyn.Modules.User.Exception;


import com.zorvyn.Modules.User.DTOs.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> UserNotFound(UserNotFoundException e){
        ErrorResponseDto errorResponseDto= ErrorResponseDto.builder().StatusCode( HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(e.getMessage()).timestamp(System.currentTimeMillis()).build();
        return  new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
