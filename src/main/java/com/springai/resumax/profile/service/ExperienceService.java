package com.springai.resumax.profile.service;

import com.springai.resumax.profile.dto.UserExperienceInsertDto;
import com.springai.resumax.profile.dto.UserExperienceUpdateDto;
import com.springai.resumax.profile.entity.UserExperience;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.ExperienceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository repository;
    private final UserProfileService profileService;

    @Transactional
    public UserExperience saveExperience(UserExperienceInsertDto dto){

        UserExperience experience = new UserExperience();

        experience.setDescription(dto.getDescription());
        experience.setTimeline(dto.getTimeline());
        experience.setOrganization(dto.getOrganization());

        UserProfile profile = profileService.fetchProfile(dto.getProfileId());
        experience.setUserProfile(profile);

        return  repository.save(experience);
    }

    @Transactional
    public UserExperience updateExperience(UserExperienceUpdateDto dto){

        UserExperience experience = repository.findById(dto.getId())
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        experience.setDescription(dto.getDescription());
        experience.setTimeline(dto.getTimeline());
        experience.setOrganization(dto.getOrganization());

        return  repository.save(experience);
    }

    @Transactional
    public UserExperience deleteExperience(Long id){

        UserExperience experience = repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        repository.delete(experience);

        return  experience;
    }

    public UserExperience fetchExperience(Long id){

        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));
    }

}
