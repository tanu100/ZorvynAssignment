package com.zorvyn.Modules.Shared.Exception;


import com.zorvyn.Modules.Shared.Exception.Dtos.ErrorResponseDto;
import com.zorvyn.Modules.Shared.Exception.Dtos.ValidationErrorDto;
import com.zorvyn.Modules.Shared.Exception.Errors.TransactionNotFoundException;
import com.zorvyn.Modules.Shared.Exception.Errors.UnauthorizedActionException;
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
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(TransactionNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedActionException e) {
        return buildResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    private ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status, String message) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .StatusCode(status.value())
                .msg(message)
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(Exception e) {
        return buildResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthentication(Exception e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Authentication failed");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
