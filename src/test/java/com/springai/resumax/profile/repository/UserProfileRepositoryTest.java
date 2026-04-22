package com.springai.resumax.profile.repository;

import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.UserProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserProfileRepositoryTest {


    @Autowired
    private  UserProfileRepository repository;
    private UserProfile userProfile;


    @BeforeEach
    void setUp(){

        userProfile = new UserProfile();

        userProfile.setUserId("1");
        userProfile.setName("sam turner");
        userProfile.setEmail("sam@gmial.com");
        userProfile.setLocation("random location");
        userProfile.setSummary("this is an random summary");
        userProfile.setLinkedIn("https://sam.linkedin.com");

        repository.save(userProfile);
    }

    @AfterEach
    void cleanUp(){

        userProfile = null;
    }

    //success test case
    @Test
    void getUserprofileByUserId(){


        UserProfile obtainedUser = repository.findByUserId("1").orElseThrow(()->
                new IllegalArgumentException("no profile found"));

        assertEquals(userProfile.getName(),obtainedUser.getName());
        assertEquals(userProfile.getLocation(),obtainedUser.getLocation());
    }

    //failure test case
    @Test
    void userProfileByUserId_notFound(){

        Exception obtainedException =
                assertThrows(IllegalArgumentException.class,()->{
                    repository.findByUserId("2").orElseThrow(
                            ()->new IllegalArgumentException("No user found"));
                });


        String expectedExceptionMsg = "No user found";
        String obtainedExceptionMsg = obtainedException.getLocalizedMessage();

        assertEquals(expectedExceptionMsg,obtainedExceptionMsg);

    }


}
