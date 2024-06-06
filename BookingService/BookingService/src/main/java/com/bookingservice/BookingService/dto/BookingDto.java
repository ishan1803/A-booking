package com.bookingservice.BookingService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Class - Entity - BookingDto
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long bookingId;
    private String userId;
    private String activityId;
}
