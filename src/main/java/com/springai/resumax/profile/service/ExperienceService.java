package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.UserExperienceInsertDto;
import com.springai.resumax.profile.dto.UserExperienceUpdateDto;
import com.springai.resumax.profile.entity.UserExperience;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.ExperienceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository repository;
    private final UserProfileService profileService;

    private final AiService aiService;

    @Transactional
    public UserExperience saveExperience(UserExperienceInsertDto dto){

        Optional<UserExperience> existingExperience =
                repository.findByDescriptionAndOrganization(dto.getDescription(),dto.getOrganization());

        if(existingExperience.isPresent()){
            throw new IllegalArgumentException("experience already exists");
        }

        UserExperience experience = new UserExperience();

        experience.setDescription(dto.getDescription());
        experience.setTimeline(dto.getTimeline());
        experience.setOrganization(dto.getOrganization());

        UserProfile profile = profileService.fetchProfile(dto.getProfileId());
        experience.setUserProfile(profile);

        experience=  repository.save(experience);

        aiService.embedExperienceDocuments(experience);

        return  experience;
    }

    @Transactional
    public UserExperience updateExperience(UserExperienceUpdateDto dto){

        UserExperience experience = repository.findById(dto.getId())
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        experience.setDescription(dto.getDescription());
        experience.setTimeline(dto.getTimeline());
        experience.setOrganization(dto.getOrganization());

        experience =  repository.save(experience);

        aiService.updateEmbedExperienceDocuments(experience);

        return experience;
    }

    @Transactional
    public void deleteExperience(Long id){

        UserExperience experience = repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        repository.delete(experience);

        aiService.deleteEmbedExperienceDocument(experience);
    }

    public UserExperience fetchExperience(Long id){

        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));
    }

}
