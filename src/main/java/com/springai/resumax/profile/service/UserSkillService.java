package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.InsertSkillDto;
import com.springai.resumax.profile.dto.RemoveSkillDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserSkill;
import com.springai.resumax.profile.repository.UserSkillRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSkillService {

    private final UserSkillRepository repository;
    private final UserProfileService profileService;
    private final AiService aiService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            skills.setSkill(skill.trim().toLowerCase(Locale.ROOT));

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

        logger.info("skills in db {}",skills);

        if(skills!=null && !skills.isEmpty()){

            Set<String> normalizedDtoSkills = dto.getSkills().stream()
                    .filter(Objects::nonNull)
                    .map(s -> s.trim().toLowerCase())
                    .collect(Collectors.toSet());

            logger.info("skills normalized {}",normalizedDtoSkills);

            List<UserSkill> skillsToDelete = skills.stream()
                    .filter(skill -> skill.getSkill() != null &&
                            normalizedDtoSkills.contains(skill.getSkill().trim().toLowerCase()))
                    .toList();

            logger.info("skills to delete {}",skillsToDelete);

            repository.deleteAll(skillsToDelete);

            logger.info("skills deleted");

            skills.removeAll(skillsToDelete);

            List<String> updatedSkills = skills.stream().map(UserSkill::getSkill).toList();

            aiService.deleteEmbedSkillsDocuments(profile.getId().toString());
            aiService.embedSkillsDocuments(String.join(",",updatedSkills),profile.getId().toString());

        }
        return  skills;
    }
}
