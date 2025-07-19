package com.eventify.event_service.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventRequest {
    private String title;
    private String description;
    private String location;
    private String category;
    private LocalDate date;
    private int totalSeats;
    private double price;
}
