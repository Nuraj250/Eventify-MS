package com.eventify.booking_service_service.service.impl;

import com.eventify.booking_service.dto.BookingDTO;
import com.eventify.booking_service.dto.CreateBookingRequest;
import com.eventify.booking_service.dto.UpdateBookingStatusRequest;
import com.eventify.booking_service.model.Booking;
import com.eventify.booking_service.model.BookingStatus;
import com.eventify.booking_service.repository.BookingRepository;
import com.eventify.booking_service.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    private final String EVENT_SERVICE_URL = "http://event-service/api/events";

    @Override
    @Transactional
    public BookingDTO createBooking(CreateBookingRequest request, Long userId) {
        // Step 1: Check availability
        String checkUrl = EVENT_SERVICE_URL + "/internal/availability?eventId=" +
                request.getEventId() + "&seats=" + request.getSeats();
        Boolean isAvailable = restTemplate.getForObject(checkUrl, Boolean.class);

        if (Boolean.FALSE.equals(isAvailable)) {
            throw new RuntimeException("Not enough seats available for this event.");
        }

        // Step 2: Reduce available seats
        String reduceUrl = EVENT_SERVICE_URL + "/internal/reduce?eventId=" +
                request.getEventId() + "&seats=" + request.getSeats();
        restTemplate.postForObject(reduceUrl, null, String.class);

        // Step 3: Save booking
        Booking booking = Booking.builder()
                .userId(userId)
                .eventId(request.getEventId())
                .seatsBooked(request.getSeats())
                .bookingDate(LocalDateTime.now())
                .status(BookingStatus.SUCCESS)
                .totalAmount(request.getSeats() * getEventPrice(request.getEventId()))
                .build();

        return mapToDTO(bookingRepository.save(booking));
    }

    @Override
    public List<BookingDTO> getMyBookings(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUserId().equals(userId)) {
            throw new RuntimeException("Not authorized to cancel this booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Override
    public void updateBookingStatus(UpdateBookingStatusRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(request.getStatus());
        bookingRepository.save(booking);
    }

    private BookingDTO mapToDTO(Booking b) {
        return BookingDTO.builder()
                .id(b.getId())
                .userId(b.getUserId())
                .eventId(b.getEventId())
                .seatsBooked(b.getSeatsBooked())
                .bookingDate(b.getBookingDate())
                .totalAmount(b.getTotalAmount())
                .status(b.getStatus())
                .build();
    }

    private double getEventPrice(Long eventId) {
        String url = EVENT_SERVICE_URL + "/" + eventId;
        // You can create an internal DTO like `EventResponseDTO` to map this cleanly
        try {
            var response = restTemplate.getForObject(url, EventResponse.class);
            return response != null ? response.getPrice() : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch event price");
        }
    }

    // Helper DTO to receive price from event-service
    @Data
    public static class EventResponse {
        private double price;
    }
}
