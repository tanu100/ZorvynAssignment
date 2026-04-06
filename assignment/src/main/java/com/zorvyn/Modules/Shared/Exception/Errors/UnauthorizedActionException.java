package com.zorvyn.Modules.Shared.Exception.Errors;

public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(String message) {
        super(message);
    }
}