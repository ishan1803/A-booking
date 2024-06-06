package com.bookingservice.BookingService.entity;

import lombok.Data;

//Class - Entity - Custom Error Object
@Data
public class CustomErrorObj {

    private Integer statusCode;

    private String message;
}

