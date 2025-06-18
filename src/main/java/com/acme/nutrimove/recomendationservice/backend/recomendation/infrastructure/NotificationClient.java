package com.acme.nutrimove.recomendationservice.backend.recomendation.infrastructure;


import com.acme.nutrimove.recomendationservice.backend.recomendation.application.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "http://localhost:8641/notifications")
public interface NotificationClient {
    @PostMapping
    NotificationDto createNotification(@RequestBody NotificationDto dto);
}
