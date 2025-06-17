package com.acme.nutrimove.recomendationservice.backend.recomendation.interfaces;

import com.acme.nutrimove.recomendationservice.backend.recomendation.domain.Recomendation;
import com.acme.nutrimove.recomendationservice.backend.recomendation.application.RecomendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecomendationController {

    @Autowired
    private RecomendationService service;

    @GetMapping
    public List<Recomendation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recomendation> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Recomendation> getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PostMapping
    public Recomendation create(@RequestBody Recomendation recomendation) {
        return service.create(recomendation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recomendation> update(@PathVariable Long id, @RequestBody Recomendation recomendation) {
        return service.update(id, recomendation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}