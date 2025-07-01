package com.acme.nutrimove.recomendationservice.backend.recomendation.interfaces;

import com.acme.nutrimove.recomendationservice.backend.recomendation.application.dto.RecomendationDto;
import com.acme.nutrimove.recomendationservice.backend.recomendation.application.services.RecomendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.acme.nutrimove.recomendationservice.backend.recomendation.domain.events.RecommendationCreatedEvent;
import com.acme.nutrimove.recomendationservice.backend.recomendation.infrastructure.broker.RecommendationEventPublisher;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recomendations")
public class RecomendationController {

    @Autowired
    private RecomendationService service;

    @Autowired
    private RecommendationEventPublisher eventPublisher;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE; // "yyyy-MM-dd"


    @GetMapping
    public List<RecomendationDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecomendationDto> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<RecomendationDto> getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<RecomendationDto> create(@RequestBody RecomendationDto dto) {
        RecomendationDto created = service.create(dto);
        // Construir evento con fecha como String (yyyy-MM-dd)
        RecommendationCreatedEvent event = new RecommendationCreatedEvent(
                created.getId(),
                created.getUserId(),
                "Nueva recomendación creada.",
                LocalDate.now().format(DATE_FORMATTER) // 👈 Conversión a String
        );
        // Enviar al message broker
        eventPublisher.publish(event);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecomendationDto> update(@PathVariable Long id, @RequestBody RecomendationDto dto) {
        return service.update(id, dto)
                .map(updated -> {
                    RecommendationCreatedEvent event = new RecommendationCreatedEvent(
                            updated.getId(),
                            updated.getUserId(),
                            "Tu recomendación ha sido actualizada.",
                            LocalDate.now().format(DATE_FORMATTER)
                    );
                    eventPublisher.publish(event);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.getById(id).map(deleted -> {
            service.delete(id);

            RecommendationCreatedEvent event = new RecommendationCreatedEvent(
                    deleted.getId(),
                    deleted.getUserId(),
                    "Tu recomendación ha sido eliminada.",
                    LocalDate.now().format(DATE_FORMATTER)
            );
            eventPublisher.publish(event);

            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
