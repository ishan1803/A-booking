package com.userservice.UserService.exceptions;

//Class - Bad Request Exception
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }

}