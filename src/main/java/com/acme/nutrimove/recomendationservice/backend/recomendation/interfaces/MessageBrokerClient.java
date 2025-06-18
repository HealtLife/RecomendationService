package com.acme.nutrimove.recomendationservice.backend.recomendation.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Define el Feign Client para consumir la API del microservicio MessageBroker
@FeignClient(name = "messagebroker-service", url = "http://localhost:8082")  // Cambia la URL según sea necesario
public interface MessageBrokerClient {

    @PostMapping("/message-broker/send")  // Endpoint para enviar un mensaje
    void sendMessage(@RequestBody String message);
}
