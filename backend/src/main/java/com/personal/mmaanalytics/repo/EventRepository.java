package com.personal.mmaanalytics.repo;

import com.personal.mmaanalytics.domain.Event;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, UUID> {
  Page<Event> findByEventDateGreaterThanEqual(LocalDate date, Pageable pageable);
}


