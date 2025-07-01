package com.acme.nutrimove.recomendationservice.backend.recomendation.domain.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RecommendationCreatedEvent implements Serializable {
    private Long recommendationId;
    private Long userId;
    private String message;
    private String timestamp;

    public RecommendationCreatedEvent(Long recommendationId, Long userId, String message, String timestamp) {
        this.recommendationId = recommendationId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public RecommendationCreatedEvent() {
    }

    public Long getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Long recommendationId) {
        this.recommendationId = recommendationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}