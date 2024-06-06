package com.experienceservice.ExperienceService.controller;


import com.experienceservice.ExperienceService.entity.Experience;
import com.experienceservice.ExperienceService.dto.ExperienceDTO;
import com.experienceservice.ExperienceService.service.ExperienceService;
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


//Class - Experience Service Controller
@RestController
@RequestMapping("/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    //Method - Test
    @GetMapping("/test")
    public String test(){
        return "Hello ";
    }

    // Method - Mapping - Customer - Writing a comment and giving a rating for an activity
    @PostMapping("/{activityId}")
    public ResponseEntity<?> writeCommentAndRating(@RequestHeader(value = "userId", required = false) String userId, @PathVariable String activityId,@Valid @RequestBody ExperienceDTO experienceDTO) {

        System.out.println("UserId "+userId);
        boolean created = experienceService.writeCommentAndRating(userId,activityId,experienceDTO);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Experience created successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create experience");
    }


    // Method - Mapping - Customer - Editing the comment and rating for an existing experience
    @PutMapping("edit/{experienceId}")
    public ResponseEntity<?> editCommentAndRating(@PathVariable Long experienceId, @Valid @RequestBody ExperienceDTO experienceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        boolean updated = experienceService.editCommentAndRating(experienceId,experienceDTO);
        if (updated) {
            return ResponseEntity.ok("Experience updated successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update experience");
    }


    // Method - Mapping - Customer - View experience details for a particular activity by ID
    @GetMapping("/byActivityId/{activityId}")
    public ResponseEntity<List<Experience>> viewExperienceDetailsByActivityId(@PathVariable String activityId) {
        List<Experience> experiences = experienceService.viewExperienceDetailsByActivityId(activityId);
        return ResponseEntity.ok(experiences);
    }


    // Method - Mapping - Admin - View all experience details

    @GetMapping("/all")
    public ResponseEntity<List<Experience>> viewAllExperienceDetailsForAdmin() {
        List<Experience> experiences = experienceService.viewAllExperienceDetailsForAdmin();
        return ResponseEntity.ok(experiences);
    }
}