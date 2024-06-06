package com.bookingservice.BookingService.exceptions;

//Class - Internal Server Error Exception
public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message){
        super(message);
    }
}