package com.experienceservice.ExperienceService;

import com.experienceservice.ExperienceService.entity.Experience;
import com.experienceservice.ExperienceService.dto.ExperienceDTO;
import com.experienceservice.ExperienceService.repository.ExperienceRepository;
import com.experienceservice.ExperienceService.service.impl.ExperienceServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//Class - Experience Service Mockito Tests
@SpringBootTest(classes = {ExperienceServiceTests.class})
public class ExperienceServiceTests {

    @Mock
    private ExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceServiceImplementation experienceService;

    private Experience sampleExperience;

    //Method - Setup before all
    @BeforeEach
    public void setUp() {
        sampleExperience = new Experience();
        sampleExperience.setExperienceId(1L);
        sampleExperience.setActivityId("123");
        sampleExperience.setUserId("456");
        sampleExperience.setRating(4);
        sampleExperience.setComment("Great experience!");
    }

    // Method - Testing - Customer - Writing a comment and giving a rating for an activity
    @Test
    public void testWriteCommentAndRating() {
        when(experienceRepository.save(any(Experience.class))).thenReturn(sampleExperience);

        String userId = "456";
        String activityId = "123";

        ExperienceDTO experienceDTO = new ExperienceDTO(1L,"123", "456", 4, "Great experience!");

        boolean saved = experienceService.writeCommentAndRating(userId, activityId,experienceDTO);

        assertTrue(saved);
    }

    // Method - Testing - Customer - Editing the comment and rating for an existing experience
    @Test
    public void testEditCommentAndRating() {
        // Scenario 1: Experience found
        when(experienceRepository.findById(1L)).thenReturn(Optional.of(sampleExperience));

        ExperienceDTO experienceDTO = new ExperienceDTO(1L, null, "5",5, "Excellent experience!");

        boolean edited = experienceService.editCommentAndRating(1L, experienceDTO);

        assertTrue(edited);

        assertEquals(5, sampleExperience.getRating());
        assertEquals("Excellent experience!", sampleExperience.getComment());

        // Scenario 2: Experience not found
        when(experienceRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> experienceService.editCommentAndRating(2L, new ExperienceDTO()));
    }


    //Method - Testing - Customer - View experience details for a particular activity by ID
    @Test
    public void testViewExperienceDetailsByActivityId() {
        List<Experience> experiences = new ArrayList<>();
        experiences.add(sampleExperience);

        when(experienceRepository.findByActivityId("123")).thenReturn(experiences);

        List<Experience> fetchedExperiences = experienceService.viewExperienceDetailsByActivityId("123");

        assertEquals(1, fetchedExperiences.size());
        assertEquals(sampleExperience, fetchedExperiences.get(0));
    }

    //Method - Testing - Admin -  View all experience details
    @Test
    public void testViewAllExperienceDetailsForAdmin() {
        List<Experience> experiences = new ArrayList<>();
        experiences.add(sampleExperience);

        when(experienceRepository.findAll()).thenReturn(experiences);

        List<Experience> fetchedExperiences = experienceService.viewAllExperienceDetailsForAdmin();

        assertEquals(1, fetchedExperiences.size());
        assertEquals(sampleExperience, fetchedExperiences.get(0));
    }
}
