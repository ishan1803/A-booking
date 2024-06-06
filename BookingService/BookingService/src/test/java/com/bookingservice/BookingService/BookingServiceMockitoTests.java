package com.bookingservice.BookingService;


import com.bookingservice.BookingService.entity.Booking;
import com.bookingservice.BookingService.exceptions.ResourceNotFoundException;
import com.bookingservice.BookingService.repository.BookingRepository;
import com.bookingservice.BookingService.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Class - Booking Service Mockito Tests
@SpringBootTest
public class BookingServiceMockitoTests {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    //Method - Testing -  Get Booking Details By User ID
    @Test
    public void testGetBookingsByUserId() {
        String userId = "1";
        List<Booking> bookings = new ArrayList<>();
        // Add some bookings to the list
        when(bookingRepository.findByUserId(userId)).thenReturn(bookings);

        List<Booking> result = bookingService.getBookingsByUserId(userId);

        assertEquals(bookings, result);
    }

    //Method - Testing - Cancel a Booking when Booking found
    @Test
    public void testCancelBooking_Success() {
        Long bookingId = 1L;
        Booking booking = new Booking(); // Create a booking
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        boolean result = bookingService.cancelBooking(bookingId);

        assertTrue(result);
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    //Method - Testing - Cancel a Booking when Booking not found
    @Test
    public void testCancelBooking_BookingNotFound() {
        Long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.cancelBooking(bookingId);
        });

        assertEquals("Booking not found with ID: " + bookingId, exception.getMessage());
        verify(bookingRepository, never()).deleteById(bookingId);
    }

    //Method - Testing - Make a Booking
    @Test
    public void testMakeBooking() {
        Long userId = 1L;
        String activityId = "someActivityId";
        // Implement your test logic for makeBooking method
    }

    //Method - Testing - Get All Booking Details
    @Test
    public void testGetAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        // Add some bookings to the list
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookings();

        assertEquals(bookings, result);
    }

    //Method - Testing - Get Booking Details By Activity ID
    @Test
    public void testGetBookingsByActivityId() {
        String activityId = "someActivityId";
        List<Booking> bookings = new ArrayList<>();
        // Add some bookings to the list
        when(bookingRepository.findAllByActivityId(activityId)).thenReturn(bookings);

        List<Booking> result = bookingService.getBookingsByActivityId(activityId);

        assertEquals(bookings, result);
    }

    //Method - Testing - Get Booking Details By Booking ID
    @Test
    public void testGetBookingById() {
        Long bookingId = 1L;
        Booking booking = new Booking(); // Create a booking
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Optional<Booking> result = bookingService.getBookingById(bookingId);

        assertTrue(result.isPresent());
        assertEquals(booking, result.get());
    }
}
