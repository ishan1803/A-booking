package com.actvityservice.ActivityService.service;

import com.actvityservice.ActivityService.dto.ActivityDTO;
import com.actvityservice.ActivityService.entity.Activity;

import java.util.List;

// Interface - Activity Service
public interface ActivityService {

    // Method - Declaring - Getting Activity Details By ID
    Activity getActivityById(String activityId);

    // Method - Declaring - Getting All Activity Details
    List<Activity> getAllActivities();

    // Method - Declaring - Getting Activity Details By Location
    List<Activity> getActivitiesByLocation(String location);

    // Method - Declaring - Getting Activity Details By Name
    List<Activity> getActivitiesByName(String name);

    // Expert ID
    List<Activity> getActivitiesByExpertId(String expertId);

    // Method - Declaring - Creating an Activity
    boolean createActivity(String expertId, ActivityDTO activitydto);

    // Method - Declaring - Updating Activity Details
    boolean updateActivityById(String expertId, String activityId, ActivityDTO activityDto);

    // Method - Declaring - Deleting an activity by ID
    boolean deleteActivityById(String expertId, String activityId);

}
