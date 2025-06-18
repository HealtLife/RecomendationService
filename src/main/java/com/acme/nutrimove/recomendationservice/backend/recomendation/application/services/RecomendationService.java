package com.acme.nutrimove.recomendationservice.backend.recomendation.application.services;

import com.acme.nutrimove.recomendationservice.backend.recomendation.domain.Recomendation;
import com.acme.nutrimove.recomendationservice.backend.recomendation.infrastructure.RecomendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecomendationService {

    @Autowired
    private RecomendationRepository repository;

    public List<Recomendation> getAll() {
        return repository.findAll();
    }

    public Optional<Recomendation> getById(Long id) {
        return repository.findById(id);
    }

    public List<Recomendation> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Recomendation create(Recomendation recomendation) {
        return repository.save(recomendation);
    }

    public Optional<Recomendation> update(Long id, Recomendation recomendation) {
        return repository.findById(id).map(existing -> {
            existing.setUserId(recomendation.getUserId());
            existing.setMessage(recomendation.getMessage());
            existing.setType(recomendation.getType());
            existing.setStatus(recomendation.getStatus());
            existing.setTimestamp(recomendation.getTimestamp());
            return repository.save(existing);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}