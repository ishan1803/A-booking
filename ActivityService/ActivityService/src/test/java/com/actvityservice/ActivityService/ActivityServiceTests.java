package com.actvityservice.ActivityService;

import com.actvityservice.ActivityService.dto.ActivityDTO;
import com.actvityservice.ActivityService.entity.Activity;
import com.actvityservice.ActivityService.exceptions.BadRequestException;
import com.actvityservice.ActivityService.exceptions.ResourceNotFoundException;
import com.actvityservice.ActivityService.repository.ActivityDBRepository;
import com.actvityservice.ActivityService.service.impl.ActivityServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


// Class - Activity Service Mockito Tests
@SpringBootTest(classes = {ActivityServiceTests.class})
public class ActivityServiceTests {

    @Mock
    ActivityDBRepository activityRepository;

    @InjectMocks
    ActivityServiceImpl activityService;

    // Method - Testing - Getting Activity Details By ID
    @Test
    public void testGetActivityById() {
        // Scenario 1: Activity found
        Activity mockActivity = new Activity("1", "Hiking", "Forest", 50, "1");
        when(activityRepository.findById("1")).thenReturn(Optional.of(mockActivity));

        Activity fetchedActivity = activityService.getActivityById("1");

        assertNotNull(fetchedActivity);
        assertEquals("Hiking", fetchedActivity.getName());
        assertEquals("Forest", fetchedActivity.getLocation());
        assertEquals(50, fetchedActivity.getPrice());
        assertEquals(1L, fetchedActivity.getExpertId());

        // Scenario 2: Activity not found
        when(activityRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> activityService.getActivityById("2"));
    }

    // Method - Testing - Creating an Activity When Input is Valid
    @Test
    public void testCreateActivity_ValidInput() {

        String expertId ="1";
        when(activityRepository.save(any(Activity.class))).thenReturn(new Activity());

        ActivityDTO activityDTO = new ActivityDTO("1","Hiking", "Forest", 50, "1");

        assertTrue(activityService.createActivity(expertId,activityDTO));
    }

    // Method - Testing - Creating an Activity When Input is Not Valid
    @Test
    public void testCreateActivity_InvalidInput() {

        ActivityDTO activityDTO = new ActivityDTO();
        String expertId ="1";

        assertThrows(BadRequestException.class, () -> activityService.createActivity(expertId,activityDTO));
    }


    // Method - Testing - Updating Activity Details By ID
    @Test
    public void testUpdateActivityById() {

        Activity mockActivityFound = new Activity("1", "Hiking", "Forest", 50, "1");
        when(activityRepository.findById("1")).thenReturn(Optional.of(mockActivityFound));

        when(activityRepository.findById("2")).thenReturn(Optional.empty());

        ActivityDTO activityDTO = new ActivityDTO("1","Hiking", "Mountain", 60, "2");

        boolean resultFound = activityService.updateActivityById("1","1",activityDTO);
        assertTrue(resultFound);

        assertEquals("Hiking", mockActivityFound.getName());
        assertEquals("Mountain", mockActivityFound.getLocation());
        assertEquals(60, mockActivityFound.getPrice());
        assertEquals(2L, mockActivityFound.getExpertId());

        assertThrows(ResourceNotFoundException.class, () -> activityService.updateActivityById("2", "2",activityDTO));
    }

    // Method - Testing - Deleting Activity Details By ID
    @Test
    public void testDeleteActivityById() {

        String activityId = "123";
        String expertId ="1";
        ActivityDTO activityDTO = new ActivityDTO("123","Hiking", "Forest", 50, "1");

        activityService.createActivity(expertId,activityDTO);
        Activity entity=new Activity();
        BeanUtils.copyProperties(activityDTO,entity);

        when(activityRepository.findById("123")).thenReturn(Optional.of(entity));
        assertTrue(activityService.deleteActivityById(expertId,entity.getActivityId()));
        verify(activityRepository, times(1)).deleteById(activityId);

    }



    // Method - Testing - Get details of all the activities
    @Test
    public void testGetAllActivities() {

        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity("1", "Hiking", "Forest", 50, "1"));
        activities.add(new Activity("2", "Cycling", "Mountain", 40, "2"));
        when(activityRepository.findAll()).thenReturn(activities);

        List<Activity> fetchedActivities = activityService.getAllActivities();

        assertNotNull(fetchedActivities);
        assertEquals(2, fetchedActivities.size());
    }


    // Method - Testing - Get All Activity Details by Location
    @Test
    public void testGetActivitiesByLocation() {

        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity("1", "Hiking", "Forest", 50, "1"));
        when(activityRepository.findByLocation("Forest")).thenReturn(activities);

        List<Activity> fetchedActivities = activityService.getActivitiesByLocation("Forest");

        assertNotNull(fetchedActivities);
        assertEquals(1, fetchedActivities.size());
    }

    // Method - Testing - Get All Activity Details by Name
    @Test
    public void testGetActivitiesByName() {

        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity("1", "Hiking", "Forest", 50, "1"));
        when(activityRepository.findByName("Hiking")).thenReturn(activities);

        List<Activity> fetchedActivities = activityService.getActivitiesByName("Hiking");

        assertNotNull(fetchedActivities);
        assertEquals(1, fetchedActivities.size());
    }

    // Method - Testing - Get All Activity Details by Expert ID
    @Test
    public void testGetActivitiesByExpertId() {

        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity("1", "Hiking", "Forest", 50, "1"));
        when(activityRepository.findByExpertId("1")).thenReturn(activities);

        List<Activity> fetchedActivities = activityService.getActivitiesByExpertId("1");

        assertNotNull(fetchedActivities);
        assertEquals(1, fetchedActivities.size());
    }
}
