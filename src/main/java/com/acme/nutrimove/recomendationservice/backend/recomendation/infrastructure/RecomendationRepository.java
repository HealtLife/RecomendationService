package com.acme.nutrimove.recomendationservice.backend.recomendation.infrastructure;

import com.acme.nutrimove.recomendationservice.backend.recomendation.domain.Recomendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecomendationRepository extends JpaRepository<Recomendation, Long> {
    List<Recomendation> findByUserId(Long userId);
}