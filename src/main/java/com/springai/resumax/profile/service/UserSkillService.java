package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.InsertSkillDto;
import com.springai.resumax.profile.dto.RemoveSkillDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserSkill;
import com.springai.resumax.profile.repository.UserSkillRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository repository;
    private final UserProfileService profileService;
    private final AiService aiService;


    @Transactional
    public List<UserSkill> saveMultipleSkill(InsertSkillDto dto){

        UserProfile profile = profileService.fetchProfile(dto.getProfileId());

        List<UserSkill> existingSkills = repository.findAllByUserProfile(profile);

        if(existingSkills!=null && !existingSkills.isEmpty()){

           boolean isMatchingSkillPresent = existingSkills.stream()
                   .anyMatch(skill->dto.getSkills().contains(skill.getSkill()));

           if(isMatchingSkillPresent){
               throw new IllegalArgumentException("Skills already present");
           }
        }


        List<UserSkill> newSkills = new ArrayList<>();

        for(String skill : dto.getSkills()){

            UserSkill skills = new UserSkill();
            skills.setUserProfile(profile);
            skills.setSkill(skill.toLowerCase(Locale.ROOT));

            newSkills.add(skills);
        }


        newSkills = repository.saveAll(newSkills);

        String embeddingSkills = String.join(",",dto.getSkills());

        aiService.embedSkillsDocuments(embeddingSkills,profile.getId().toString());

        return newSkills;
    }

    @Transactional
    public List<UserSkill> removeSingleSkill(RemoveSkillDto dto){

        UserProfile profile = profileService.fetchProfile(dto.getProfileId());

        List<UserSkill> skills = repository.findAllByUserProfile(profile);

        if(skills!=null && !skills.isEmpty()){

            skills.removeIf(skill->dto.getSkills().contains(skill.getSkill()));

            skills = repository.saveAll(skills);

            List<String> updatedSkills = skills.stream().map(UserSkill::getSkill).toList();

            aiService.deleteEmbedSkillsDocuments(profile.getId().toString());
            aiService.embedSkillsDocuments(String.join(",",updatedSkills),profile.getId().toString());

        }
        return  skills;
    }
}
