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

    @Autowired
    private MessageBrokerClient messageBrokerClient;  // Usar el cliente Feign para comunicarse con MessageBroker

    // Obtener todas las recomendaciones
    @GetMapping
    public List<Recomendation> getAll() {
        return service.getAll();
    }

    // Obtener una recomendación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Recomendation> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener recomendaciones por ID de usuario
    @GetMapping("/user/{userId}")
    public List<Recomendation> getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    // Crear una nueva recomendación y enviar un mensaje a MessageBroker
    @PostMapping
    public Recomendation create(@RequestBody Recomendation recomendation) {
        // Crear la recomendación
        Recomendation createdRecomendation = service.create(recomendation);

        // Enviar un mensaje a MessageBroker (microservicio externo)
        String message = "New recommendation created: " + createdRecomendation.getId();
        messageBrokerClient.sendMessage(message);  // Llamada al microservicio MessageBroker

        return createdRecomendation;
    }

    // Actualizar una recomendación y enviar un mensaje a MessageBroker
    @PutMapping("/{id}")
    public ResponseEntity<Recomendation> update(@PathVariable Long id, @RequestBody Recomendation recomendation) {
        return service.update(id, recomendation)
                .map(updatedRecomendation -> {
                    // Enviar mensaje a MessageBroker notificando que la recomendación ha sido actualizada
                    String message = "Recommendation updated: " + updatedRecomendation.getId();
                    messageBrokerClient.sendMessage(message);  // Llamada al microservicio MessageBroker

                    return ResponseEntity.ok(updatedRecomendation);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una recomendación y enviar un mensaje a MessageBroker
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        // Enviar un mensaje a MessageBroker notificando que la recomendación ha sido eliminada
        String message = "Recommendation deleted: " + id;
        messageBrokerClient.sendMessage(message);  // Llamada al microservicio MessageBroker

        return ResponseEntity.noContent().build();
    }
}
