package com.bookingservice.BookingService.exceptions;

// Class - Unauthorized Exception
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){
        super(message);
    }
}