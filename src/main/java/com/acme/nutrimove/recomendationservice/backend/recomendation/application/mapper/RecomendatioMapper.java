package com.acme.nutrimove.recomendationservice.backend.recomendation.application.mapper;

import com.acme.nutrimove.recomendationservice.backend.recomendation.application.dto.RecomendationDto;
import com.acme.nutrimove.recomendationservice.backend.recomendation.domain.Recomendation;
import org.springframework.stereotype.Component;

@Component
public class RecomendatioMapper {

    public Recomendation toEntity(RecomendationDto dto){
        return new Recomendation(
                dto.getUserId(),
                dto.getNutritionistId(),
                dto.getMessage(),
                dto.getAnswer(),
                dto.getType(),
                dto.getStatus(),
                dto.getTimestamp()
        );
    }

    public RecomendationDto toDto(Recomendation recomendation){
        RecomendationDto dto = new RecomendationDto();
        dto.setUserId(recomendation.getUserId());
        dto.setNutritionistId(recomendation.getNutritionistId());
        dto.setMessage(recomendation.getMessage());
        dto.setAnswer(recomendation.getAnswer());
        dto.setType(recomendation.getType());
        dto.setStatus(recomendation.getStatus());
        dto.setTimestamp(recomendation.getTimestamp());
        return dto;
    }
}
/*    private Long userId;
    private Long nutritionistId;
    private String message;
    private String answer;
    private String type;
    private String status;
    private LocalDateTime timestamp;*/