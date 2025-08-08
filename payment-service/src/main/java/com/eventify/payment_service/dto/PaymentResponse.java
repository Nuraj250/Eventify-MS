package com.eventify.payment_service.dto;

import com.eventify.payment_service.model.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private Long bookingId;
    private Long userId;
    private Double amount;
    private PaymentStatus status;
    private String method;
    private LocalDateTime paymentDate;
}
