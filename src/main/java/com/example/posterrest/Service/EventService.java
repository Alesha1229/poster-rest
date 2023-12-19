package com.example.posterrest.Service;

import com.example.posterrest.Entity.Event;
import com.example.posterrest.Entity.User;
import com.example.posterrest.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event, User createdBy) {
        event.setCreatedBy(createdBy);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAllByOrderByStartDateAsc();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return eventRepository.findAllByStartDateBetweenOrderByStartDateAsc(startDate, endDate);
    }
}
