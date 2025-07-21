package com.eventify.booking_service.dto;

import com.eventify.booking.model.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long eventId;
    private int seatsBooked;
    private double totalAmount;
    private LocalDateTime bookingDate;
    private BookingStatus status;
}
