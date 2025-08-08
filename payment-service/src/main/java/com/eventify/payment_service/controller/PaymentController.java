package com.eventify.payment_service.controller;

import com.eventify.payment_service.dto.PaymentRequest;
import com.eventify.payment_service.dto.PaymentResponse;
import com.eventify.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.makePayment(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUser(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentResponse>> getAll() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}
