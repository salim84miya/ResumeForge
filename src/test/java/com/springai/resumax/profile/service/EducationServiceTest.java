package com.springai.resumax.profile.service;

import com.springai.resumax.profile.dto.EducationInsertDto;
import com.springai.resumax.profile.dto.EducationUpdateDto;
import com.springai.resumax.profile.entity.UserEducation;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.EducationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EducationServiceTest {


    @Mock
    private EducationRepository educationRepository;


    @InjectMocks
    private EducationService educationService;

    @Mock
    private UserProfileService userProfileService;



    private UserEducation education;
    private EducationInsertDto educationInsertDto;
    private EducationUpdateDto educationUpdateDto;
    private UserProfile userProfile;

    @BeforeEach
    void setUp() {

        education = new UserEducation();

        education.setGrade("A");
        education.setQualification("BE computer science");
        education.setTimeline("2021-2025");

        educationInsertDto = new EducationInsertDto();

        educationInsertDto.setGrade("A");
        educationInsertDto.setQualification("BE computer science");
        educationInsertDto.setTimeline("2021-2025");
        educationInsertDto.setProfileId(1L);

        educationUpdateDto = new EducationUpdateDto();

        educationUpdateDto.setId(1L);
        educationUpdateDto.setGrade("B");
        educationUpdateDto.setQualification("BE computer science");
        educationUpdateDto.setTimeline("2021-2025");

        userProfile = new UserProfile();

        userProfile.setUserId("1");
        userProfile.setName("sam turner");
        userProfile.setEmail("sam@gmial.com");
        userProfile.setLocation("random location");
        userProfile.setSummary("this is an random summary");
        userProfile.setLinkedIn("https://sam.linkedin.com");

    }

    @AfterEach
    void tearDown() {

        education = null;
        educationInsertDto = null;
        educationUpdateDto = null;
        userProfile = null;
    }

    @Test
    void saveEducation() {

        when(educationRepository.save(any(UserEducation.class))).thenReturn(education);
        when(userProfileService.fetchProfile(1L)).thenReturn(userProfile);

         UserEducation obtainedUserEducation = educationService.saveEducation(educationInsertDto);

        assertEquals(obtainedUserEducation.getQualification(),education.getQualification());
        assertEquals(obtainedUserEducation.getGrade(),education.getGrade());


    }

    @Test
    void updateEducation() {

        education.setGrade("B");

        when(educationRepository.findById(1L)).thenReturn(Optional.ofNullable(education));
        when(educationRepository.save(ArgumentMatchers.any(UserEducation.class))).thenReturn(education);

        UserEducation updatedEducation = educationService.updateEducation(educationUpdateDto);

        assertEquals(updatedEducation.getGrade(),education.getGrade());
    }

    @Test
    void deleteEducation() {

        when(educationRepository.findById(1L)).thenReturn(Optional.ofNullable(education));
        doNothing().when(educationRepository).delete(education);

        educationService.deleteEducation(1L);

        verify(educationRepository,times(1)).delete(education);

    }

    @Test
    void getEducation() {

        when(educationRepository.findById(1L)).thenReturn(Optional.ofNullable(education));

        UserEducation obtainedEducation = educationService.getEducation(1L);

        assertEquals(obtainedEducation.getQualification(),education.getQualification());
    }


    @Test
    void testGetEducation_throwException(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->{
           educationService.getEducation(2L);
        });

        assertEquals("Education data not found",exception.getLocalizedMessage());

    }
}