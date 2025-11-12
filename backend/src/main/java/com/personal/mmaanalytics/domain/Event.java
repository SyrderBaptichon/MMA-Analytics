package com.personal.mmaanalytics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {
  @Id
  private UUID id;

  @Column(name = "external_ref", unique = true)
  private String externalRef;

  @Column(nullable = false)
  private String name;

  @Column(name = "event_date")
  private LocalDate eventDate;

  private String location;

  public Event() {}

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public String getExternalRef() { return externalRef; }
  public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public LocalDate getEventDate() { return eventDate; }
  public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
  public String getLocation() { return location; }
  public void setLocation(String location) { this.location = location; }
}


