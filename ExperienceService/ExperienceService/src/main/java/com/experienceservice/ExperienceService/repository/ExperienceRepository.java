package com.experienceservice.ExperienceService.repository;

import com.experienceservice.ExperienceService.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Class - Experience Service Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    // Finding experiences by Customer ID
    List<Experience> findByUserId(String userId);

    // Finding experiences by Activity ID
    List<Experience> findByActivityId(String activityId);

    // Finding all experiences for a particular activity
    List<Experience> findAllByActivityId(String activityId);
}

