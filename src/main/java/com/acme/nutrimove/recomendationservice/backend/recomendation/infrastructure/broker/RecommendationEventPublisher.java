package com.acme.nutrimove.recomendationservice.backend.recomendation.infrastructure.broker;

import com.acme.nutrimove.recomendationservice.backend.recomendation.domain.events.RecommendationCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecommendationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.broker.exchange.recommendation}")
    private String exchange;

    @Value("${app.broker.routing-key.recommendation.created}")
    private String routingKey;

    public RecommendationEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(RecommendationCreatedEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}