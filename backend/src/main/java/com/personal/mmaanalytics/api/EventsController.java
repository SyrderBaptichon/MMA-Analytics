package com.personal.mmaanalytics.api;

import com.personal.mmaanalytics.api.dto.EventDto;
import com.personal.mmaanalytics.domain.Bout;
import com.personal.mmaanalytics.domain.Event;
import com.personal.mmaanalytics.repo.BoutRepository;
import com.personal.mmaanalytics.repo.EventRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventsController {
  private final EventRepository eventRepository;
  private final BoutRepository boutRepository;

  public EventsController(EventRepository eventRepository, BoutRepository boutRepository) {
    this.eventRepository = eventRepository;
    this.boutRepository = boutRepository;
  }

  @GetMapping
  public Page<EventDto> list(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size) {
    return eventRepository.findAll(PageRequest.of(page, size)).map(this::toDto);
  }

  @GetMapping("upcoming")
  public Page<EventDto> upcoming(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size) {
    return eventRepository
        .findByEventDateGreaterThanEqual(LocalDate.now(), PageRequest.of(page, size))
        .map(this::toDto);
  }

  @GetMapping("{id}")
  public EventDto byId(@PathVariable("id") UUID id) {
    Event ev = eventRepository.findById(id).orElseThrow();
    return toDto(ev);
  }

  @GetMapping("{id}/bouts")
  public List<Bout> bouts(@PathVariable("id") UUID id) {
    Event ev = eventRepository.findById(id).orElseThrow();
    return boutRepository.findByEvent(ev);
  }

  private EventDto toDto(Event e) {
    EventDto dto = new EventDto();
    dto.id = e.getId();
    dto.name = e.getName();
    dto.eventDate = e.getEventDate();
    dto.location = e.getLocation();
    return dto;
  }
}


