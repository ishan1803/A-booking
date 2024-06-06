package com.actvityservice.ActivityService.service.impl;

import com.actvityservice.ActivityService.dto.ActivityDTO;
import com.actvityservice.ActivityService.entity.Activity;
import com.actvityservice.ActivityService.exceptions.BadRequestException;
import com.actvityservice.ActivityService.exceptions.ResourceNotFoundException;
import com.actvityservice.ActivityService.exceptions.UnauthorizedException;
import com.actvityservice.ActivityService.repository.ActivityDBRepository;
import com.actvityservice.ActivityService.service.ActivityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

// Class - Implementing Activity Service Inteface
@Log4j2
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDBRepository activityRepository;

    private static Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    // Method - Implementing - Getting Activity Details By ID
    @Override
    public Activity getActivityById(String activityId) {
        Optional<Activity> fetchedItem= activityRepository.findById(activityId);
        if (fetchedItem.isPresent()) {
            logger.info("Activity found with ID: {}", activityId);
        } else {
            logger.warn("No activity found with ID: {}", activityId);
        }
        return fetchedItem.orElseThrow(()->new ResourceNotFoundException("No activity with given id!"));
    }

    // Method - Implementing - Getting All Activity Details
    @Override
    public List<Activity> getAllActivities() {
        logger.info("Fetching all activities");
        List<Activity> activities = activityRepository.findAll();
        logger.info("Number of activities fetched: {}", activities.size());
        return activities;
    }

    // Method - Implementing - Getting Activity Details By Location
    @Override
    public List<Activity> getActivitiesByLocation(String location) {
        logger.info("Fetching activities by location: {}", location);
        List<Activity> activities = activityRepository.findByLocation(location);
        logger.info("Number of activities found for location {}: {}", location, activities.size());
        return activities;
    }

    // Method - Implementing - Getting Activity Details By Name
    @Override
    public List<Activity> getActivitiesByName(String name) {
        logger.info("Fetching activities by name: {}", name);
        List<Activity> activities = activityRepository.findByName(name);
        logger.info("Number of activities found with name {}: {}", name, activities.size());
        return activities;
    }

    // Method - Implementing - Getting Activity Details By Expert ID
    @Override
    public List<Activity> getActivitiesByExpertId(String expertId) {
        logger.info("Fetching activities by expert ID: {}", expertId);
        List<Activity> activities = activityRepository.findByExpertId(expertId);
        logger.info("Number of activities found for expert ID {}: {}", expertId, activities.size());
        return activities;
    }

    // Method - Implementing - Creating an Activity
    @Override
    public boolean createActivity(String expertId,ActivityDTO activitydto) {
        if (activitydto.getName() == null || activitydto.getName().isEmpty() ||
                activitydto.getLocation() == null || activitydto.getLocation().isEmpty() ||
                activitydto.getPrice() <= 0 ) {
            logger.error("Invalid activity data: Name, Location, Price, and Expert ID cannot be empty. Price and Expert ID should be > 0.");
            throw new BadRequestException("Name, Location, Price and  Expert ID cannot be empty, Price and Expert ID should be > 0.");
        }

        logger.info("Service Method -> createActivity()");
        logger.info("Parameters Inside Service Method -> expertId : " + expertId + " activitydto : " + activitydto);

        Activity activity = new Activity();

        BeanUtils.copyProperties(activitydto,activity);
        activity.setExpertId(expertId);

        logger.info("Activity Object -> " + activity);

        try {
            Activity savedActivity = activityRepository.save(activity);
            logger.info("Activity created with ID: {}", savedActivity.getExpertId());
            return true;
        } catch (Exception e) {
            logger.error("Failed to create activity: {}", e.getMessage());
            return false;
        }
    }


    // Method - Implementing - Updating Activity Details
    @Override
    public boolean updateActivityById(String expertId,String activityId, ActivityDTO activityDTO) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> {
                    logger.warn("Activity not found with ID: {}", activityId);
                    return new ResourceNotFoundException("Activity not found with ID: " + activityId);
                });

        if(!expertId.equals(activity.getExpertId())) throw new UnauthorizedException("You are not authorized to create Activities!");

        activity.setName(activityDTO.getName());
        activity.setLocation(activityDTO.getLocation());
        activity.setPrice(activityDTO.getPrice());
        activity.setExpertId(activityDTO.getExpertId());

        activityRepository.save(activity);
        logger.info("Activity updated with ID: {}", activityId);

        return true;
    }


    // Method - Implementing - Deleting Activity Details By ID
    @Override
    public boolean deleteActivityById(String expertId,String activityId) {
        logger.info("Deleting activity with ID: {}", activityId);
        Optional<Activity> fetchedActivity = activityRepository.findById(activityId);
        if (fetchedActivity.isPresent()) {
            if(!expertId.equals(fetchedActivity.get().getExpertId())) throw new UnauthorizedException("You are not authorized to create Activities!");
            activityRepository.deleteById(activityId);
            logger.info("Activity deleted with ID: {}", activityId);
            return true;
        } else {
            logger.warn("Cannot find activity with given ID: {}", activityId);
            throw new ResourceNotFoundException("Cannot find activity with given ID!");
        }
    }


}
