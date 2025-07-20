package com.eventify.event_service.service;

import com.eventify.event.dto.CreateEventRequest;
import com.eventify.event.dto.EventDTO;

import java.util.List;

public interface EventService {

    EventDTO createEvent(CreateEventRequest request);

    EventDTO updateEvent(Long id, CreateEventRequest request);

    void deleteEvent(Long id);

    List<EventDTO> getAllEvents();

    EventDTO getEventById(Long id);

    List<EventDTO> searchEvents(String category, String location);

    boolean checkAvailability(Long eventId, int requestedSeats);

    void reduceAvailableSeats(Long eventId, int seatsToReduce);
}
