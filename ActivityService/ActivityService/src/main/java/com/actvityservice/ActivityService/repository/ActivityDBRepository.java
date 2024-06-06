package com.actvityservice.ActivityService.repository;

import com.actvityservice.ActivityService.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

// Interface - Activity Repository

public interface ActivityDBRepository extends MongoRepository<Activity, String> {


    // Find activities by location
    List<Activity> findByLocation(String location);

    // Find activities by name
    List<Activity> findByName(String name);

    // Find all activities
    List<Activity> findAll();

    // Find activity by ID
    Optional<Activity> findById( String activityId);

    // Find activities by expert ID
    List<Activity> findByExpertId(String expertId);

}
