package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.UserProjectInsertDto;
import com.springai.resumax.profile.dto.UserProjectUpdateDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserProject;
import com.springai.resumax.profile.repository.ProjectRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private AiService aiService;

    private UserProject project;
    private UserProjectInsertDto projectInsertDto;
    private UserProjectUpdateDto projectUpdateDto;
    private UserProfile userProfile;


    @BeforeEach
    void setUp() {

        project = new UserProject();

        project.setName("project 1");
        project.setDescription("This is an description for project 1");
        project.setLink("https://projects.com");
        project.setTimeline("Apr 2026 - May 2026");

        projectInsertDto = new UserProjectInsertDto();

        projectInsertDto.setName("project 1");
        projectInsertDto.setDescription("This is an description for project 1");
        projectInsertDto.setLink("https://projects.com");
        projectInsertDto.setTimeline("Apr 2026 - May 2026");
        projectInsertDto.setProfileId(1L);

        projectUpdateDto =new UserProjectUpdateDto();

        projectUpdateDto.setName("project 1");
        projectUpdateDto.setDescription("This is an description for project 1");
        projectUpdateDto.setLink("https://projects.com");
        projectUpdateDto.setTimeline("Apr 2026 - May 2026");
        projectUpdateDto.setId(1L);

        userProfile = new UserProfile();

        userProfile.setId(1L);
        userProfile.setName("sam turner");
        userProfile.setEmail("sam@gmial.com");
        userProfile.setLocation("random location");
        userProfile.setSummary("this is an random summary");
        userProfile.setLinkedIn("https://sam.linkedin.com");
    }

    @AfterEach
    void tearDown() {

        project = null;
        projectInsertDto = null;
        projectUpdateDto = null;
        userProfile = null;

    }

    @Test
    void saveProject() {

        when(userProfileService.fetchProfile(projectInsertDto.getProfileId()))
                .thenReturn(userProfile);

        when(projectRepository.save(ArgumentMatchers.any(UserProject.class)))
                .thenReturn(project);

        UserProject obtainedProject =  projectService.saveProject(projectInsertDto);

        assertEquals(project.getName(),obtainedProject.getName());

        verify(aiService,times(1)).embedProjectDocuments(project);
    }

    @Test
    void updateProject() {

        project.setName("updated project");

        when(projectRepository.findById(projectUpdateDto.getId()))
                .thenReturn(Optional.ofNullable(project));

        when(projectRepository.save(ArgumentMatchers.any(UserProject.class))).thenReturn(project);

        UserProject updatedProject = projectService.updateProject(projectUpdateDto);

        assertEquals(project.getName(),updatedProject.getName());

        verify(aiService,times(1)).updateEmbedProjectDocuments(updatedProject);
    }

    @Test
    void deleteProject() {

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project));

        doNothing().when(projectRepository).delete(project);

        projectService.deleteProject(1L);

        verify(projectRepository,times(1)).delete(project);

        verify(aiService,times(1)).deleteEmbedProjectDocuments(project);
    }

    @Test
    void fetchProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project));

        UserProject fetchedProject = projectService.fetchProject(1L);

        assertEquals(project.getName(),fetchedProject.getName());
    }
}