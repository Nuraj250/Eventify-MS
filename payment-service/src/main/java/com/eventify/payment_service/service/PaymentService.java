package com.eventify.payment_service.service;

import com.eventify.payment_service.dto.PaymentRequest;
import com.eventify.payment_service.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest request);
    List<PaymentResponse> getPaymentsByUser(Long userId);
    List<PaymentResponse> getAllPayments();
}
