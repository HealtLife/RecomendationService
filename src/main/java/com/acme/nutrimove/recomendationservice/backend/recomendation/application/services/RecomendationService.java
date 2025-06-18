package com.acme.nutrimove.recomendationservice.backend.recomendation.application.services;

import com.acme.nutrimove.recomendationservice.backend.recomendation.application.dto.RecomendationDto;
import com.acme.nutrimove.recomendationservice.backend.recomendation.application.mapper.RecomendatioMapper;
import com.acme.nutrimove.recomendationservice.backend.recomendation.domain.Recomendation;
import com.acme.nutrimove.recomendationservice.backend.recomendation.infrastructure.RecomendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecomendationService {

    @Autowired
    private RecomendationRepository repository;

    @Autowired
    private RecomendatioMapper mapper;

    public List<RecomendationDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<RecomendationDto> getById(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public List<RecomendationDto> getByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public RecomendationDto create(RecomendationDto dto) {
        Recomendation entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public Optional<RecomendationDto> update(Long id, RecomendationDto dto) {
        return repository.findById(id).map(existing -> {
            existing.setUserId(dto.getUserId());
            existing.setNutritionistId(dto.getNutritionistId());
            existing.setMessage(dto.getMessage());
            existing.setAnswer(dto.getAnswer());
            existing.setType(dto.getType());
            existing.setStatus(dto.getStatus());
            existing.setTimestamp(dto.getTimestamp());
            return mapper.toDto(repository.save(existing));
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}