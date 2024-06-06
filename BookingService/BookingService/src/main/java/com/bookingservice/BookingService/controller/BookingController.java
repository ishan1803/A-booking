package com.bookingservice.BookingService.controller;


import com.bookingservice.BookingService.dto.BookingDto;
import com.bookingservice.BookingService.entity.Booking;
import com.bookingservice.BookingService.exceptions.ResourceNotFoundException;
import com.bookingservice.BookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class - Booking Controller
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Method - Mapping - Get Booking Details By User ID
    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable String userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/test")
    public String test(){
        return "Hello ";
    }

    // Method - Mapping - Cancel a Booking
    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        boolean canceled = bookingService.cancelBooking(bookingId);
        if (canceled) {
            return new ResponseEntity<>("Booking canceled successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to cancel booking", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Method - Mapping - Make a Booking
    @PostMapping("/make/{activityId}")
    public ResponseEntity<?> makeBooking(@RequestHeader(value = "userId", required = false) String userId, @PathVariable String activityId) {

        boolean made = bookingService.makeBooking(userId, activityId);
        if (made) {
            return new ResponseEntity<>("Booking made successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to make booking", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Method - Mapping - Get All Booking Details
    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }

    // Method - Mapping - Get Booking Details By Activity ID
    @GetMapping("/byActivityId/{activityId}")
    public ResponseEntity<?> getBookingsByActivityId(@PathVariable String activityId) {
        List<Booking> bookings = bookingService.getBookingsByActivityId(activityId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }

    // Method - Mapping - Get Booking Details By Booking ID for Expert
    @GetMapping("/forExpert/getById/{bookingId}")
    public ResponseEntity<?> getBookingByIdforExpert(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        return ResponseEntity.ok(booking);
    }

    // Method - Mapping - Get Booking Details By Booking ID for Customer
    @GetMapping("/forCustomer/getById/{bookingId}")
    public ResponseEntity<?> getBookingByIdforCustomer(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        return ResponseEntity.ok(booking);
    }

    // Method - Mapping - Get Booking Details By Booking ID for Admin
    @GetMapping("/forAdmin/getById/{bookingId}")
    public ResponseEntity<?> getBookingByIdforAdmin(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        return ResponseEntity.ok(booking);
    }
}
