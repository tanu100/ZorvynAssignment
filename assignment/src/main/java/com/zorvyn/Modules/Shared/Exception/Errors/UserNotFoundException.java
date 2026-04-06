package com.zorvyn.Modules.Shared.Exception.Errors;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg){
        super(msg);
    }
}
