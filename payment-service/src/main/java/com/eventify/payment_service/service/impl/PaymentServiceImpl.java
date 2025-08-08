package com.eventify.payment_service.service.impl;

import com.eventify.payment_service.dto.PaymentRequest;
import com.eventify.payment_service.dto.PaymentResponse;
import com.eventify.payment_service.model.Payment;
import com.eventify.payment_service.model.PaymentStatus;
import com.eventify.payment_service.repository.PaymentRepository;
import com.eventify.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {
        Payment payment = Payment.builder()
                .bookingId(request.getBookingId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status(PaymentStatus.SUCCESS) // Simulate always successful
                .method(request.getMethod())
                .paymentDate(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    @Override
    public List<PaymentResponse> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .bookingId(payment.getBookingId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .build();
    }
}
