package com.actvityservice.ActivityService.dto;

import lombok.Data;

// Class - Entity - ActiviyDTO
@Data
public class ActivityDTO {

    private String activityId;

    private String name;

    private String location;

    private double price;

    private String expertId; // Foreign Key referencing the User entity

    private String imageUrl;


    public ActivityDTO(String activityId, String name, String location, double price, String expertId) {
        this.activityId = activityId;
        this.name = name;
        this.location = location;
        this.price = price;
        this.expertId = expertId;
    }

    public ActivityDTO() {

    }
}
