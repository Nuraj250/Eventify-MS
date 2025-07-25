package com.eventify.booking_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
public class BookingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookingServiceApplication.class, args);
	}
}
