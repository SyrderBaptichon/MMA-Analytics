package com.personal.mmaanalytics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "bouts")
public class Bout {
  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @ManyToOne
  @JoinColumn(name = "fighter_red_id", nullable = false)
  private Fighter fighterRed;

  @ManyToOne
  @JoinColumn(name = "fighter_blue_id", nullable = false)
  private Fighter fighterBlue;

  @Column(name = "weight_class")
  private String weightClass;

  private String result;
  private String method;
  private Integer round;
  private String time;

  public Bout() {}

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public Event getEvent() { return event; }
  public void setEvent(Event event) { this.event = event; }
  public Fighter getFighterRed() { return fighterRed; }
  public void setFighterRed(Fighter fighterRed) { this.fighterRed = fighterRed; }
  public Fighter getFighterBlue() { return fighterBlue; }
  public void setFighterBlue(Fighter fighterBlue) { this.fighterBlue = fighterBlue; }
  public String getWeightClass() { return weightClass; }
  public void setWeightClass(String weightClass) { this.weightClass = weightClass; }
  public String getResult() { return result; }
  public void setResult(String result) { this.result = result; }
  public String getMethod() { return method; }
  public void setMethod(String method) { this.method = method; }
  public Integer getRound() { return round; }
  public void setRound(Integer round) { this.round = round; }
  public String getTime() { return time; }
  public void setTime(String time) { this.time = time; }
}


