package com.eventify.booking_service.dto;

import com.eventify.booking.model.BookingStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookingStatusRequest {
    private Long bookingId;
    private BookingStatus status;
}
