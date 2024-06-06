package com.actvityservice.ActivityService.entity;

import lombok.Data;

// Class - Entity - Custom Error Object
@Data
public class CustomErrorObj {
    private Integer statusCode;
    private String message;
}