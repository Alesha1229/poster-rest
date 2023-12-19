package com.example.posterrest.Controller;

import com.example.posterrest.Entity.Event;
import com.example.posterrest.Entity.User;
import com.example.posterrest.Service.EventService;
import com.example.posterrest.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping("create/{userId}")
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            Event createdEvent = eventService.createEvent(event, user.get());
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/date/{startDate}/{endDate}")
    public ResponseEntity<List<Event>> getEventsBetweenDates(@PathVariable LocalDate startDate,
                                                             @PathVariable LocalDate endDate) {
        List<Event> events = eventService.getEventsBetweenDates(startDate, endDate);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}