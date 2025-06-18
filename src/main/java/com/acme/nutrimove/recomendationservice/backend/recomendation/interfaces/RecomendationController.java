package com.acme.nutrimove.recomendationservice.backend.recomendation.interfaces;

import com.acme.nutrimove.recomendationservice.backend.recomendation.application.dto.NotificationDto;
import com.acme.nutrimove.recomendationservice.backend.recomendation.application.dto.RecomendationDto;
import com.acme.nutrimove.recomendationservice.backend.recomendation.domain.Recomendation;
import com.acme.nutrimove.recomendationservice.backend.recomendation.application.services.RecomendationService;
import com.acme.nutrimove.recomendationservice.backend.recomendation.infrastructure.NotificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recomendations")
public class RecomendationController {

    @Autowired
    private RecomendationService service;

    @Autowired
    private MessageBrokerClient messageBrokerClient;

    @Autowired
    private NotificationClient notificationClient;


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
        //messageBrokerClient.sendMessage("New recommendation created: " + created.getUserId());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecomendationDto> update(@PathVariable Long id, @RequestBody RecomendationDto dto) {
        return service.update(id, dto)
                .map(updated -> {
                    // Crear la notificación asociada
                    NotificationDto notification = new NotificationDto();
                    notification.setUserId(updated.getUserId());
                    notification.setMessage("Tu recomendación ha sido actualizada.");
                    notification.setType("RECOMMENDATION");
                    notification.setStatus("UNREAD");
                    notification.setTimestamp(java.time.LocalDateTime.now());

                    notificationClient.createNotification(notification);

                    messageBrokerClient.sendMessage("Recommendation updated: " + updated.getUserId());

                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        messageBrokerClient.sendMessage("Recommendation deleted: " + id);
        return ResponseEntity.noContent().build();
    }
}
