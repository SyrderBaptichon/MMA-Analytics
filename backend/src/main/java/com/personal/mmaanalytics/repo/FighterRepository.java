package com.personal.mmaanalytics.repo;

import com.personal.mmaanalytics.domain.Fighter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FighterRepository extends JpaRepository<Fighter, UUID> {
  @Query("select f from Fighter f where lower(f.name) like lower(concat('%', :q, '%'))")
  Page<Fighter> searchByName(String q, Pageable pageable);
}


