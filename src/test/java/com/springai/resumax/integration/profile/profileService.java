package com.springai.resumax.integration.profile;

import com.springai.resumax.profile.dto.UserProfileInsertDto;
import com.springai.resumax.profile.dto.UserProfileUpdateDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.service.UserProfileService;
import com.springai.resumax.security.dto.SignupDto;
import com.springai.resumax.security.entity.User;
import com.springai.resumax.security.service.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class profileService {


    @Autowired
    private UserProfileService profileService;

    @Autowired
    private AuthService authService;

    private UserProfileInsertDto profile;
    private SignupDto dto;
    private UserProfile userProfile;

    @BeforeEach
    void setUp(){


        dto = new SignupDto();
        dto.setUsername("sam");
        dto.setPassword("abcd@12345");

        profile = new UserProfileInsertDto();

        profile.setName("sam turner");
        profile.setEmail("sam@gmial.com");
        profile.setLocation("random location");
        profile.setSummary("this is an random summary");
        profile.setLinkedIn("https://sam.linkedin.com");

        User user = authService.signup(dto);

        profile.setUserId(user.getId());

       userProfile = profileService.saveProfile(profile);

    }

    @AfterEach
    void tearDown(){
        dto = null;
        profile = null;
        userProfile = null;
    }

    @Test
    public void test_profileCreatedSuccessfully(){



        assertEquals(profile.getEmail(),userProfile.getEmail());

    }

    @Test
    public void test_profileUpdatedSuccessfully(){

        UserProfileUpdateDto updateDto = new UserProfileUpdateDto();

        updateDto.setId(userProfile.getId());
        updateDto.setName("max");
        updateDto.setEmail(userProfile.getEmail());
        updateDto.setSummary(userProfile.getSummary());
        updateDto.setLocation(userProfile.getLocation());
        updateDto.setLinkedIn(userProfile.getLinkedIn());

        UserProfile updatedProfile = profileService.updateProfile(updateDto);

        assertEquals(updateDto.getName(),updatedProfile.getName());

    }

    @Test
    public void test_profileDeletedSuccessfully(){

        profileService.deleteProfile(userProfile.getId());

//        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class,()->{
//            profileService.fetchProfile(userProfile.getId());
//        }
//        );
//
//        assertEquals("no data found",exception.getMessage());


    }

    @Test
    public void test_profileFetchedSuccessfully(){

        UserProfile fetchedProfile = profileService.fetchProfile(userProfile.getId());

        assertEquals(userProfile.getId(),fetchedProfile.getId());
    }

    @Test
    public void test_profileFetchedThrowException(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->{
            profileService.fetchProfile(105L);
        });


        assertEquals("no data found",exception.getMessage());
    }
}
