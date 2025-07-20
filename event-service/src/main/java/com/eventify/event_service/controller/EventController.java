package com.eventify.event_service.controller;

import com.eventify.event.dto.CreateEventRequest;
import com.eventify.event.dto.EventDTO;
import com.eventify.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // 🔐 ADMIN: Create Event
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDTO> create(@RequestBody CreateEventRequest request) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    // 🔐 ADMIN: Update Event
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody CreateEventRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    // 🔐 ADMIN: Delete Event
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }

    // ✅ PUBLIC: Get All Events
    @GetMapping
    public ResponseEntity<List<EventDTO>> all() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // ✅ PUBLIC: Get Event by ID
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    // 🔎 Search (by category OR location)
    @GetMapping("/search")
    public ResponseEntity<List<EventDTO>> search(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location
    ) {
        return ResponseEntity.ok(eventService.searchEvents(category, location));
    }

    // 🔒 Internal use: Check seat availability
    @GetMapping("/internal/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long eventId,
            @RequestParam int seats
    ) {
        return ResponseEntity.ok(eventService.checkAvailability(eventId, seats));
    }

    // 🔒 Internal use: Reduce seats
    @PostMapping("/internal/reduce")
    public ResponseEntity<String> reduceSeats(
            @RequestParam Long eventId,
            @RequestParam int seats
    ) {
        eventService.reduceAvailableSeats(eventId, seats);
        return ResponseEntity.ok("Seats reduced successfully");
    }
}
