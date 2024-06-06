package com.bookingservice.BookingService.service.impl;

import com.bookingservice.BookingService.entity.Booking;
import com.bookingservice.BookingService.exceptions.BadRequestException;
import com.bookingservice.BookingService.exceptions.ResourceNotFoundException;
import com.bookingservice.BookingService.repository.BookingRepository;
import com.bookingservice.BookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

//Class - Booking Service Implementation
@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    @Autowired
    private BookingRepository bookingDBRepository ;

    // Method - Implementing - Get Booking Details By User ID
    @Override
    public List<Booking> getBookingsByUserId(String userId) {
        logger.info("Fetching bookings for user with ID: {}", userId);
        return bookingDBRepository.findByUserId(userId);
    }

    // Method - Implementing - Cancel a Booking
    @Override
    public boolean cancelBooking(Long bookingId) {
        logger.info("Canceling booking with ID: {}", bookingId);
        Booking booking = bookingDBRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        bookingDBRepository.deleteById(bookingId);
        logger.info("Booking successfully canceled with ID: {}", bookingId);
        return true;
    }

    // Method - Implementing - Make a Booking
    @Override
    public boolean makeBooking( String userId, String activityId) {
        logger.info("Making booking for user with ID: {} and activity with ID: {}", userId, activityId);

        if (userId == null || activityId == null) {
            logger.error("UserId or ActivityId cannot be null!");
            throw new BadRequestException("UserId or ActivityId cannot be null!");
        }

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setActivityId(activityId);

        try {
            Booking savedBooking = bookingDBRepository.save(booking);
            logger.info("Booking successfully made for user with ID: {} and activity with ID: {}", userId, activityId);
            return true;
        } catch (Exception e) {
            logger.error("Error making booking for user with ID: {} and activity with ID: {}", userId, activityId, e);
            return false;
        }
    }

    // Method - Implementing - Get All Booking Details
    @Override
    public List<Booking> getAllBookings() {
        logger.info("Fetching all bookings");
        return bookingDBRepository.findAll();
    }

    // Method - Implementing - Get Booking Details By Activity ID
    @Override
    public List<Booking> getBookingsByActivityId(String activityId) {
        logger.info("Fetching bookings for activity with ID: {}", activityId);
        return bookingDBRepository.findAllByActivityId(activityId);
    }

    // Method - Implementing - Get Booking Details By Booking ID
    @Override
    public Optional<Booking> getBookingById(Long bookingId) {
        logger.info("Fetching booking with ID: {}", bookingId);
        return bookingDBRepository.findById(bookingId);
    }

}
