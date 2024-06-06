package com.userservice.UserService.exceptions;

//Class - Forbidden Exception
public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}
