package com.eventify.event_service.service.impl;

import com.eventify.event.dto.CreateEventRequest;
import com.eventify.event.dto.EventDTO;
import com.eventify.event.model.Event;
import com.eventify.event.repository.EventRepository;
import com.eventify.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventDTO createEvent(CreateEventRequest request) {
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .category(request.getCategory())
                .date(request.getDate())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .price(request.getPrice())
                .build();

        Event saved = eventRepository.save(event);
        return mapToDTO(saved);
    }

    @Override
    public EventDTO updateEvent(Long id, CreateEventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setCategory(request.getCategory());
        event.setDate(request.getDate());
        event.setTotalSeats(request.getTotalSeats());
        event.setAvailableSeats(request.getTotalSeats()); // reset availability
        event.setPrice(request.getPrice());

        return mapToDTO(eventRepository.save(event));
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return mapToDTO(event);
    }

    @Override
    public List<EventDTO> searchEvents(String category, String location) {
        if (category != null) {
            return eventRepository.findByCategory(category)
                    .stream().map(this::mapToDTO).collect(Collectors.toList());
        } else if (location != null) {
            return eventRepository.findByLocationContainingIgnoreCase(location)
                    .stream().map(this::mapToDTO).collect(Collectors.toList());
        } else {
            return getAllEvents();
        }
    }

    @Override
    public boolean checkAvailability(Long eventId, int requestedSeats) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return event.getAvailableSeats() >= requestedSeats;
    }

    @Override
    public void reduceAvailableSeats(Long eventId, int seatsToReduce) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        int currentAvailable = event.getAvailableSeats();
        if (currentAvailable < seatsToReduce) {
            throw new RuntimeException("Not enough seats available");
        }

        event.setAvailableSeats(currentAvailable - seatsToReduce);
        eventRepository.save(event);
    }

    private EventDTO mapToDTO(Event e) {
        return EventDTO.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .location(e.getLocation())
                .category(e.getCategory())
                .date(e.getDate())
                .totalSeats(e.getTotalSeats())
                .availableSeats(e.getAvailableSeats())
                .price(e.getPrice())
                .build();
    }
}
