package com.bookingservice.BookingService.exceptions;

// Class - Resource Not Found Exception
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}