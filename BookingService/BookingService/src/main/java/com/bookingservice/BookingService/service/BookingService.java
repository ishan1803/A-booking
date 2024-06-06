package com.bookingservice.BookingService.service;

import com.bookingservice.BookingService.entity.Booking;

import java.util.List;
import java.util.Optional;

//Class - Booking Service Interface
public interface BookingService {

    // Method - Declaring - Get Booking Details By User ID
    List<Booking> getBookingsByUserId(String userId);

    // Method - Declaring - Cancel a Booking
    boolean cancelBooking(Long bookingId);

    // Method - Declaring - Make a Booking
    boolean makeBooking(String userId, String activityId);

    // Method - Declaring - Get All Booking Details
    List<Booking> getAllBookings();

    // Method - Declaring - Get Booking Details By Activity ID
    List<Booking> getBookingsByActivityId(String activityId);

    // Method - Declaring - Get Booking Details By Booking ID
    Optional<Booking> getBookingById(Long bookingId);
}
