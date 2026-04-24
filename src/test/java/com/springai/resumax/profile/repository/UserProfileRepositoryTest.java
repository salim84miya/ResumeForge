package com.springai.resumax.profile.repository;

import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.security.entity.User;
import com.springai.resumax.security.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserProfileRepositoryTest {

//
//    @Autowired
//    private  UserProfileRepository repository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private UserProfile userProfile;
//    private User user;
//    private User user2;
//
//
//    @BeforeEach
//    void setUp(){
//
//        user = new User();
//
//        user.setUsername("sam");
//
//        user2 = new User();
//
//        user2.setUsername("max");
//
//
//
//        userProfile = new UserProfile();
//
//        userProfile.setId(1L);
//        userProfile.setName("sam turner");
//        userProfile.setEmail("sam@gmial.com");
//        userProfile.setLocation("random location");
//        userProfile.setSummary("this is an random summary");
//        userProfile.setLinkedIn("https://sam.linkedin.com");
//
//        repository.save(userProfile);
//
//        userProfile = user.setUserProfile(userProfile);
//
//        userRepository.save(user);
//        userRepository.save(user2);
//
//    }
//
//    @AfterEach
//    void cleanUp(){
//
//        userProfile = null;
//    }
//
//    //success test case
//    @Test
//    void getUserprofileByUserId(){
//
//
//
//        UserProfile obtainedUser = repository.findByUser(user).orElseThrow(()->
//                new IllegalArgumentException("no profile found"));
//
//        assertEquals(userProfile.getName(),obtainedUser.getName());
//        assertEquals(userProfile.getLocation(),obtainedUser.getLocation());
//    }
//
//    //failure test case
//    @Test
//    void userProfileByUserId_notFound(){
//
//        Exception obtainedException =
//                assertThrows(IllegalArgumentException.class,()->{
//                    repository.findByUser(new User()).orElseThrow(
//                            ()->new IllegalArgumentException("No profile found"));
//                });
//
//
//        String expectedExceptionMsg = "No profile found";
//        String obtainedExceptionMsg = obtainedException.getLocalizedMessage();
//
//        assertEquals(expectedExceptionMsg,obtainedExceptionMsg);
//
//    }


}
