package com.personal.mmaanalytics.repo;

import com.personal.mmaanalytics.domain.Bout;
import com.personal.mmaanalytics.domain.Event;
import com.personal.mmaanalytics.domain.Fighter;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoutRepository extends JpaRepository<Bout, UUID> {
  List<Bout> findByEvent(Event event);
  List<Bout> findByFighterRedOrFighterBlue(Fighter red, Fighter blue);
}


