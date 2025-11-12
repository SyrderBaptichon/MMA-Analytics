package com.personal.mmaanalytics.api;

import com.personal.mmaanalytics.api.dto.FighterDto;
import com.personal.mmaanalytics.domain.Fighter;
import com.personal.mmaanalytics.repo.FighterRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fighters")
public class FightersController {
  private final FighterRepository fighterRepository;

  public FightersController(FighterRepository fighterRepository) {
    this.fighterRepository = fighterRepository;
  }

  @GetMapping
  public Page<FighterDto> list(
      @RequestParam(name = "q", required = false, defaultValue = "") String q,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size) {
    return fighterRepository.searchByName(q, PageRequest.of(page, size)).map(this::toDto);
  }

  @GetMapping("{id}")
  public FighterDto byId(@PathVariable("id") UUID id) {
    Fighter f = fighterRepository.findById(id).orElseThrow();
    return toDto(f);
  }

  private FighterDto toDto(Fighter f) {
    FighterDto dto = new FighterDto();
    dto.id = f.getId();
    dto.name = f.getName();
    dto.nickname = f.getNickname();
    dto.stance = f.getStance();
    dto.heightCm = f.getHeightCm();
    dto.reachCm = f.getReachCm();
    dto.dateOfBirth = f.getDateOfBirth();
    return dto;
  }
}


