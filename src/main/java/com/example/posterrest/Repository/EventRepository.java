package com.example.posterrest.Repository;

import com.example.posterrest.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);
    List<Event> findAllByOrderByStartDateAsc();
    List<Event> findAllByStartDateBetweenOrderByStartDateAsc(LocalDateTime startDate, LocalDateTime endDate);
}