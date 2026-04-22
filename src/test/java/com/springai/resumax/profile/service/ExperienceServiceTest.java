package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.UserExperienceInsertDto;
import com.springai.resumax.profile.dto.UserExperienceUpdateDto;
import com.springai.resumax.profile.entity.UserExperience;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.ExperienceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceTest {


    @Mock
    private ExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceService experienceService;

    @Mock
    private AiService aiService;

    @Mock
    private UserProfileService profileService;

    private UserProfile userProfile;
    private UserExperience experience;
    private UserExperienceInsertDto insertDto;
    private UserExperienceUpdateDto updateDto;

    @BeforeEach
    void setUp() {

       insertDto = new UserExperienceInsertDto();

       insertDto.setOrganization("Self / Personal Projects");
       insertDto.setDescription("Developed scalable backend systems using Spring Boot and microservices architecture. Implemented JWT-based authentication with refresh tokens and multi-device session management, improving system security. Optimized API performance using Redis caching, reducing response time by 35%.");
       insertDto.setTimeline("Jan 2025 – Present");
       insertDto.setProfileId(1L);


       updateDto = new UserExperienceUpdateDto();

       updateDto.setTimeline("Jan 2025 – Present");
       updateDto.setOrganization("Self / Personal Projects");
       updateDto.setDescription("Developed scalable backend systems using Spring Boot and microservices architecture. Implemented JWT-based authentication with refresh tokens and multi-device session management, improving system security. Optimized API performance using Redis caching, reducing response time by 35%.");
       updateDto.setProfileId(1L);
       updateDto.setId(1L);


        userProfile = new UserProfile();
        userProfile.setUserId("1");
        userProfile.setName("sam turner");
        userProfile.setEmail("sam@gmial.com");
        userProfile.setLocation("random location");
        userProfile.setSummary("this is an random summary");
        userProfile.setLinkedIn("https://sam.linkedin.com");

        experience = new UserExperience();

        experience.setTimeline("Feb 2025 – Present");
        experience.setOrganization("Self / Personal Projects");
        experience.setDescription("Developed scalable backend systems using Spring Boot and microservices architecture. Implemented JWT-based authentication with refresh tokens and multi-device session management, improving system security. Optimized API performance using Redis caching, reducing response time by 35%.");
        experience.setUserProfile(userProfile);

    }

    @AfterEach
    void tearDown() {

        userProfile = null;
        experience = null;
        insertDto = null;
        updateDto =null;

    }

    @Test
    void test_saveExperienceSuccessfully() {

        Mockito.when(experienceRepository.save(ArgumentMatchers.any(UserExperience.class))).thenReturn(experience);
        Mockito.when(experienceRepository.findByDescriptionAndOrganization(insertDto.getDescription(),insertDto.getOrganization()))
                .thenReturn(Optional.empty());

        Mockito.when(profileService.fetchProfile(1L)).thenReturn(userProfile);

        UserExperience newExperience =  experienceService.saveExperience(insertDto);

        verify(aiService,times(1)).embedExperienceDocuments(experience);

        verify(experienceRepository,times(1)).findByDescriptionAndOrganization(insertDto.getDescription(),insertDto.getOrganization());

        assertEquals(insertDto.getOrganization(),newExperience.getOrganization());
    }

    @Test
    void test_updateExperienceSuccessfully() {

        experience.setTimeline("Feb 2025 – Present");

        Mockito.when(experienceRepository.save(ArgumentMatchers.any(UserExperience.class))).thenReturn(experience);
        Mockito.when(experienceRepository.findById(1L)).thenReturn(Optional.ofNullable(experience));

        UserExperience updatedExperience = experienceService.updateExperience(updateDto);

        verify(aiService,times(1)).updateEmbedExperienceDocuments(updatedExperience);

        assertEquals(updateDto.getTimeline(),updatedExperience.getTimeline());
    }

    @Test
    void test_deleteExperienceSuccessfully() {

        Mockito.when(experienceRepository.findById(1L)).thenReturn(Optional.ofNullable(experience));
        doNothing().when(experienceRepository).delete(experience);

        experienceService.deleteExperience(1L);

        verify(aiService,times(1)).deleteEmbedExperienceDocument(experience);

        verify(experienceRepository,times(1)).delete(experience);
    }

    @Test
    void fetchExperience() {

        Mockito.when(experienceRepository.findById(1L)).thenReturn(Optional.ofNullable(experience));

        UserExperience fetchedExperience = experienceService.fetchExperience(1L);

        assertEquals(experience.getOrganization(),fetchedExperience.getOrganization());
    }
}