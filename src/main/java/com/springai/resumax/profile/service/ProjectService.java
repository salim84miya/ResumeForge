package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.UserProjectInsertDto;
import com.springai.resumax.profile.dto.UserProjectUpdateDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserProject;
import com.springai.resumax.profile.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {


    private final ProjectRepository repository;
    private final UserProfileService profileService;
    private final AiService aiService;

    @Transactional
    public UserProject saveProject(UserProjectInsertDto dto){

        UserProject project = new UserProject();

        project.setName(dto.getName());
        project.setTimeline(dto.getTimeline());
        project.setLink(dto.getLink());
        project.setDescription(dto.getDescription());

        UserProfile profile = profileService.fetchProfile(dto.getProfileId());
        project.setUserProfile(profile);

        project = repository.save(project);

        aiService.embedProjectDocuments(project);

        return  project;
    }

    @Transactional
    public UserProject updateProject(UserProjectUpdateDto dto){

        UserProject project = repository.findById(dto.getId())
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        project.setName(dto.getName());
        project.setTimeline(dto.getTimeline());
        project.setLink(dto.getLink());
        project.setDescription(dto.getDescription());


        return repository.save(project);
    }

    @Transactional
    public void deleteProject(Long id){

        UserProject project = repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));

       repository.delete(project);


//        return project;
    }

    public UserProject fetchProject(Long id){

        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));

    }



}
