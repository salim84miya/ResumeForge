package com.springai.resumax.integration.project;

import com.springai.resumax.profile.dto.UserProfileInsertDto;
import com.springai.resumax.profile.dto.UserProjectInsertDto;
import com.springai.resumax.profile.dto.UserProjectUpdateDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserProject;
import com.springai.resumax.profile.service.ProjectService;
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

@SpringBootTest
public class ProjectServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ProjectService projectService;

    private UserProfileInsertDto profile;
    private SignupDto dto;

    private UserProjectInsertDto insertDto;
    private UserProject project;

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

        insertDto = new UserProjectInsertDto();

        insertDto.setName("project 1");
        insertDto.setDescription("This is an description for project 1");
        insertDto.setTimeline("Apr 2026 - May 2026");
        insertDto.setLink("https://projects.com");

        User user = authService.signup(dto);

        profile.setUserId(user.getId());

        UserProfile userProfile = userProfileService.saveProfile(profile);

        insertDto.setProfileId(userProfile.getId());

        project = projectService.saveProject(insertDto);
    }

    @AfterEach
    void cleanUp(){
        profile=null;
        dto=null;

        insertDto=null;

        projectService.deleteProject(project.getId());
        project=null;

    }

    @Test
    public void test_projectCreatedSuccessfully(){

        assertEquals(insertDto.getName(),project.getName());
    }

    @Test
    public void test_projectUpdatedSuccessfully(){

        UserProjectUpdateDto updateDto = new UserProjectUpdateDto();

        updateDto.setId(project.getId());
        updateDto.setTimeline(project.getTimeline());
        updateDto.setDescription(project.getDescription());
        updateDto.setLink(project.getLink());
        updateDto.setName("New updated project name");

        UserProject updatedProject = projectService.updateProject(updateDto);

        assertEquals(updateDto.getName(),updatedProject.getName());
    }
}
