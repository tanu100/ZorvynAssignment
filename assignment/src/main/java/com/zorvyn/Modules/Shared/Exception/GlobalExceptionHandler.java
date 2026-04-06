package com.zorvyn.Modules.Shared.Exception;


import com.zorvyn.Modules.Shared.Exception.Dtos.ErrorResponseDto;
import com.zorvyn.Modules.Shared.Exception.Dtos.ValidationErrorDto;
import com.zorvyn.Modules.Shared.Exception.Errors.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> UserNotFound(UserNotFoundException e){
        ErrorResponseDto errorResponseDto= ErrorResponseDto.builder().StatusCode( HttpStatus.NOT_FOUND.value())
                .msg(e.getMessage()).timestamp(System.currentTimeMillis()).build();
        return  new ResponseEntity<>(errorResponseDto,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDto> ValidationsError(MethodArgumentNotValidException e){
        HashMap<String,String> errors= new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.put(fieldError.getField(),fieldError.getDefaultMessage())
        );

        ValidationErrorDto validationErrorDto = ValidationErrorDto.builder()
                .map(errors).StatusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(validationErrorDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> DatabaseErrors(DataIntegrityViolationException e){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .msg(e.getMessage()).timestamp(System.currentTimeMillis()).StatusCode(HttpStatus.CONFLICT.value())
                .build();

        return new ResponseEntity<>(errorResponseDto,HttpStatus.CONFLICT);
    }

}
