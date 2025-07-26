package com.eventify.booking_service.controller;

import com.eventify.booking_service.dto.BookingDTO;
import com.eventify.booking_service.dto.CreateBookingRequest;
import com.eventify.booking_service.dto.UpdateBookingStatusRequest;
import com.eventify.booking_service.service.BookingService;
import com.eventify.booking_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final JwtUtil jwtUtil;

    //   USER: Book Event
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingDTO> bookEvent(@RequestBody CreateBookingRequest request, HttpServletRequest httpRequest) {
        Long userId = jwtUtil.extractUserIdFromToken(httpRequest);
        return ResponseEntity.ok(bookingService.createBooking(request, userId));
    }

    //   USER: View My Bookings
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingDTO>> myBookings(HttpServletRequest httpRequest) {
        Long userId = jwtUtil.extractUserIdFromToken(httpRequest);
        return ResponseEntity.ok(bookingService.getMyBookings(userId));
    }

    //   USER: Cancel Booking
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> cancel(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long userId = jwtUtil.extractUserIdFromToken(httpRequest);
        bookingService.cancelBooking(id, userId);
        return ResponseEntity.ok("Booking cancelled");
    }

    //   ADMIN: View All Bookings
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDTO>> all() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    //   ADMIN/Internal: Update Booking Status
    @PatchMapping("/status")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> updateStatus(@RequestBody UpdateBookingStatusRequest request) {
        bookingService.updateBookingStatus(request);
        return ResponseEntity.ok("Booking status updated");
    }
}
