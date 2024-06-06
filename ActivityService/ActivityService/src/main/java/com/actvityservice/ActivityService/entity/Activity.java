package com.actvityservice.ActivityService.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Class - Entity - Activity
@Document("activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    private String activityId;

    private String name;

    private String location;

    private double price;

    private String expertId;

    private String imageUrl;
}