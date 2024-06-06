package com.experienceservice.ExperienceService.service.impl;

import com.experienceservice.ExperienceService.dto.ExperienceDTO;
import com.experienceservice.ExperienceService.entity.Experience;
import com.experienceservice.ExperienceService.repository.ExperienceRepository;
import com.experienceservice.ExperienceService.service.ExperienceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImplementation implements ExperienceService {

    private static final Logger logger = LoggerFactory.getLogger(ExperienceServiceImplementation.class);
    @Autowired
    private ExperienceRepository repository;

    // Method - Implementing - Writing comment and rating
    @Override
    public boolean writeCommentAndRating(String userId, String activityId, ExperienceDTO experienceDTO) {
        logger.info("Writing comment and rating for activity ID: {}", activityId);

        if (activityId == null || userId == null ) {
            logger.error("Invalid input data");
            throw new IllegalArgumentException("Invalid input data");
        }

        if(experienceDTO.getRating() <= 0 || experienceDTO.getRating() > 5)
        {
            logger.error("Invalid input data");
            logger.info("Rating should not be greater than 5 or less than 1");
            throw new IllegalArgumentException("Invalid input data");
        }

        Experience experience = new Experience();
        experience.setUserId(userId);
        experience.setActivityId(activityId);
        BeanUtils.copyProperties(experienceDTO, experience);
        try {
            Experience savedExperience = repository.save(experience);
            logger.info("Comment and rating successfully written for activity ID: {}", activityId);
            return true;
        } catch (Exception e) {
            logger.error("Error writing comment and rating for activity ID: {}", activityId);
            return false;
        }
    }

    // Method - Implementing - Editing comment and rating
    @Override
    public boolean editCommentAndRating(Long experienceId, ExperienceDTO experienceDTO) {
        logger.info("Editing comment and rating for experience ID: {}", experienceId);

        Optional<Experience> optionalExperience = repository.findById(experienceId);
        if (!optionalExperience.isPresent()) {
            logger.error("Experience not found with ID: {}", experienceId);
            throw new IllegalArgumentException("Experience not found with ID: " + experienceId);
        }

        Experience experience = optionalExperience.get();
        experience.setRating(experienceDTO.getRating());
        experience.setComment(experienceDTO.getComment());
        repository.save(experience);
        logger.info("Comment and rating successfully edited for experience ID: {}", experienceId);
        return true;
    }

    // Method - Implementing - Customer - View experience details for a particular activity by ID
    @Override
    public List<Experience> viewExperienceDetailsByActivityId(String activityId) {
        logger.info("Viewing experience details for activity ID: {}", activityId);
        return repository.findByActivityId(activityId);
    }

    // Method - Implementing - Admin - View all experience details
    @Override
    public List<Experience> viewAllExperienceDetailsForAdmin() {
        logger.info("Viewing all experience details for admin");
        return repository.findAll();
    }

}
