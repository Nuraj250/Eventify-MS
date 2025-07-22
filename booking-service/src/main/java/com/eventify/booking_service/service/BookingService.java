package com.eventify.booking_service.service;

import com.eventify.booking.dto.BookingDTO;
import com.eventify.booking.dto.CreateBookingRequest;
import com.eventify.booking.dto.UpdateBookingStatusRequest;

import java.util.List;

public interface BookingService {

    BookingDTO createBooking(CreateBookingRequest request, Long userId);

    List<BookingDTO> getMyBookings(Long userId);

    List<BookingDTO> getAllBookings();

    void cancelBooking(Long bookingId, Long userId);

    void updateBookingStatus(UpdateBookingStatusRequest request);
}
