package com.personal.mmaanalytics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fighters")
public class Fighter {
  @Id
  private UUID id;

  @Column(name = "external_ref", unique = true)
  private String externalRef;

  @Column(nullable = false)
  private String name;

  private String nickname;
  private String stance;
  @Column(name = "height_cm")
  private Integer heightCm;
  @Column(name = "reach_cm")
  private Integer reachCm;
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  public Fighter() {}

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public String getExternalRef() { return externalRef; }
  public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getNickname() { return nickname; }
  public void setNickname(String nickname) { this.nickname = nickname; }
  public String getStance() { return stance; }
  public void setStance(String stance) { this.stance = stance; }
  public Integer getHeightCm() { return heightCm; }
  public void setHeightCm(Integer heightCm) { this.heightCm = heightCm; }
  public Integer getReachCm() { return reachCm; }
  public void setReachCm(Integer reachCm) { this.reachCm = reachCm; }
  public LocalDate getDateOfBirth() { return dateOfBirth; }
  public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}


