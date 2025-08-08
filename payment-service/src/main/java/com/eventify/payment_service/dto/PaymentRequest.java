package com.eventify.payment_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private Long bookingId;
    private Long userId;
    private Double amount;
    private String method; // CARD, STRIPE, etc.
}
