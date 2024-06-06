package com.bookingservice.BookingService.repository;

import com.bookingservice.BookingService.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Class - Booking Repository
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Finding All Bookings By User ID
    List<Booking> findByUserId(String userId);

    // Finding All Bookings User By Activity ID
    List<Booking> findAllByActivityId(String activityId);

    // Finding All Bookings User By Booking ID
    Optional<Booking> findById(Long bookingId);

    // Finding All Bookings
    List<Booking> findAll();
}
