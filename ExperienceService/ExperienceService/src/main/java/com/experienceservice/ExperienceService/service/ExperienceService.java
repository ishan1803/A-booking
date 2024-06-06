package com.experienceservice.ExperienceService.service;

import com.experienceservice.ExperienceService.dto.ExperienceDTO;
import com.experienceservice.ExperienceService.entity.Experience;

import java.util.List;

//Class - Experience Service
public interface ExperienceService {

    //Method - Declaring - Customer - Writing a comment and giving a rating for an activity
    boolean writeCommentAndRating(String userId, String activityId, ExperienceDTO experienceDTO);

    //Method - Declaring - Customer - Editing a comment and rating for an activity
    boolean editCommentAndRating(Long experienceId,ExperienceDTO experienceDTO);


    //Method - Declaring - Customer/Admin/Expert - View experience details for a particular activity by ID
    List<Experience> viewExperienceDetailsByActivityId(String activityId);


    //Method - Declaring - Admin -  View all experience details
    List<Experience> viewAllExperienceDetailsForAdmin();
}
