package com.userservice.UserService.exceptions;

//Class -
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}