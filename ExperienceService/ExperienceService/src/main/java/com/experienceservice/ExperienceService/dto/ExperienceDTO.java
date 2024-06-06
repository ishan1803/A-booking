package com.experienceservice.ExperienceService.dto;

import lombok.Data;

// Class - DTO - ExperienceDTO
@Data
public class ExperienceDTO {

    private Long experienceId;

    private int rating;

    private String comment;

    public ExperienceDTO(Long experienceId, String activityId, String userId, int rating, String comment) {
        this.experienceId = experienceId;
        this.rating = rating;
        this.comment = comment;
    }

    public ExperienceDTO() {

    }
}
