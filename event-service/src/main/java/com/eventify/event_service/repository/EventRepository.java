package com.eventify.event_service.repository;

import com.eventify.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCategory(String category);

    List<Event> findByDateAfter(LocalDate date);

    List<Event> findByLocationContainingIgnoreCase(String location);
}
