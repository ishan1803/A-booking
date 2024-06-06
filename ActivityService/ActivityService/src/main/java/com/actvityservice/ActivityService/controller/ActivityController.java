package com.actvityservice.ActivityService.controller;

import com.actvityservice.ActivityService.dto.ActivityDTO;
import com.actvityservice.ActivityService.entity.Activity;

import com.actvityservice.ActivityService.service.ActivityService;
import com.actvityservice.ActivityService.service.impl.ActivityServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Class - Activity Controller

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private static Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    private ActivityService activityService;

    //Method - Mapping - Get Activity Details by ID for Customer
    @GetMapping("forCustomer/{activityId}")
    public ResponseEntity<?> getActivityByIdforCustomer(@PathVariable("activityId") String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        return ResponseEntity.ok(activity);
    }

    //Method - Mapping - Get Activity Details by ID for Admin
    @GetMapping("forAdmin/{activityId}")
    public ResponseEntity<?> getActivityByIdforAdmin(@PathVariable("activityId") String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        return ResponseEntity.ok(activity);
    }

    //Method - Mapping - Get Activity Details by ID for Expert
    @GetMapping("forExpert/{activityId}")
    public ResponseEntity<?> getActivityByIdforAdminforExpert(@PathVariable("activityId") String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        return ResponseEntity.ok(activity);
    }

    @GetMapping("/test")
    public String test(){
        return "Hello ";
    }

    //Method - Mapping - Get All Activity Details
    @GetMapping("/All")
    public ResponseEntity<?> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activities);
    }

    //Method - Mapping - Create an Activity
    @PostMapping("/add")
    public ResponseEntity<?> createActivity(@RequestHeader("userId") String expertId,@Valid @RequestBody ActivityDTO activityDTO, BindingResult bindingResult) {
        logger.info("Endpoint Called -> /activities/add");
        logger.info("Parameters -> expertId : " + expertId + " activityDto : " + activityDTO);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        boolean created = activityService.createActivity(expertId,activityDTO);

        if (created) {
            return new ResponseEntity<>("Activity created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create activity", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Method - Mapping - Update Activity Details by ID
    @PutMapping("/{activityId}")
    public ResponseEntity<?> updateActivityById(@RequestHeader("userId") String expertId,@PathVariable String activityId, @Valid @RequestBody ActivityDTO activityDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        boolean updated = activityService.updateActivityById(expertId,activityId, activityDTO);
        if (updated) {
            return new ResponseEntity<>("Activity updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update activity", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Method - Mapping - Delete an Activity by ID
    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> deleteActivityById(@RequestHeader("userId") String expertId,@PathVariable String activityId) {
        boolean deleted = activityService.deleteActivityById(expertId,activityId);
        if (deleted) {
            return new ResponseEntity<>("Activity deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete activity", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Method - Mapping - Get Activity Details by Location
    @GetMapping("/byLocation/{location}")
    public ResponseEntity<?> getActivitiesByLocation(@PathVariable String location) {
        List<Activity> activities = activityService.getActivitiesByLocation(location);
        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activities);
    }

    //Method - Mapping - Get Activity Details by Name
    @GetMapping("/byName/{name}")
    public ResponseEntity<?> getActivitiesByName(@PathVariable String name) {
        List<Activity> activities = activityService.getActivitiesByName(name);
        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activities);
    }

    //Method - Mapping - Get Activity Details by Expert ID
    @GetMapping("/byExpert/{expertId}")
    public ResponseEntity<?> getActivitiesByExpertId(@PathVariable String expertId) {
        List<Activity> activities = activityService.getActivitiesByExpertId(expertId);
        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activities);
    }

}
