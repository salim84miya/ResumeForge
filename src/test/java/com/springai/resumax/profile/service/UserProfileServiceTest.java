package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.UserProfileInsertDto;
import com.springai.resumax.profile.dto.UserProfileUpdateDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.UserProfileRepository;
import com.springai.resumax.security.entity.User;
import com.springai.resumax.security.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AiService aiService;

    private UserProfile userProfile;

    private UserProfileInsertDto profileDto;

    private UserProfileUpdateDto updateProfileDto;

    private User user;

    @BeforeEach
    void setUp() {

        profileDto = new UserProfileInsertDto();

        profileDto.setName("sam turner");
        profileDto.setEmail("sam@gmial.com");
        profileDto.setLocation("random location");
        profileDto.setSummary("this is an random summary");
        profileDto.setSkills("writing,java,spring boot");
        profileDto.setLinkedIn("https://sam.linkedin.com");

        updateProfileDto = new UserProfileUpdateDto();

        updateProfileDto.setId(1L);
        updateProfileDto.setUserId("1");
        updateProfileDto.setName("sam turner");
        updateProfileDto.setEmail("sam@gmial.com");
        updateProfileDto.setLocation("random location");
        updateProfileDto.setSummary("this is an random summary");
        updateProfileDto.setSkills("writing,java,spring boot");
        updateProfileDto.setLinkedIn("https://sam.linkedin.com");

        userProfile = new UserProfile();

        userProfile.setId(1L);
        userProfile.setName("sam turner");
        userProfile.setEmail("sam@gmial.com");
        userProfile.setLocation("random location");
        userProfile.setSummary("this is an random summary");
        userProfile.setLinkedIn("https://sam.linkedin.com");

        user = new User();

        user.setUsername("sam");
        user.setId(1L);

    }

    @AfterEach
    void tearDown() {

        userProfile = null;
    }

    @Test
    void saveProfile() {

        when(userRepository.findById(profileDto.getUserId())).thenReturn(Optional.ofNullable(user));
        when(userProfileRepository.save(ArgumentMatchers.any(UserProfile.class))).thenReturn(userProfile);
        UserProfile obtainedProfile = userProfileService.saveProfile(profileDto);

        assertEquals(obtainedProfile.getId().toString(),userProfile.getId().toString());

        verify(aiService,times(1)).embedProfileDocuments(userProfile);
    }

    @Test
    void updateProfile() {

        when(userProfileRepository.save(ArgumentMatchers.any(UserProfile.class))).thenReturn(userProfile);
        when(userProfileRepository.findById(1L)).thenReturn(Optional.ofNullable(userProfile));

        UserProfile obtainedProfile = userProfileService.updateProfile(updateProfileDto);

        assertEquals(obtainedProfile.getId().toString(),userProfile.getId().toString());

        verify(aiService,times(1)).updateEmbedProfileDocuments(userProfile);
    }

    @Test
    void deleteProfile() {

        when(userProfileRepository.findById(1L)).thenReturn(Optional.ofNullable(userProfile));
        doNothing().when(userProfileRepository).delete(userProfile);

        userProfileService.deleteProfile(1L);

        verify(userProfileRepository,times(1)).delete(userProfile);

        verify(aiService,times(1)).deleteEmbedProfileDocuments(userProfile);

    }

    @Test
    void fetchProfile() {

       when(userProfileRepository.findById(1L)).thenReturn(Optional.ofNullable(userProfile));
        UserProfile obtainedProfile = userProfileService.fetchProfile(1L);

        assertEquals(obtainedProfile.getId(),userProfile.getId());

    }
}