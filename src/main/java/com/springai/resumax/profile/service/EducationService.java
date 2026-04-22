package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.EducationInsertDto;
import com.springai.resumax.profile.dto.EducationUpdateDto;
import com.springai.resumax.profile.entity.UserEducation;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.EducationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserProfileService userProfileService;

    private final AiService aiService;

    @Transactional
    public UserEducation saveEducation(EducationInsertDto dto){

        Optional<UserEducation> existingEducation =
                educationRepository.findByQualification(dto.getQualification());

        if(existingEducation.isPresent()){
            throw new IllegalArgumentException("qualification already exists!");
        }

        UserEducation education = new UserEducation();

        education.setGrade(dto.getGrade());
        education.setQualification(dto.getQualification());
        education.setTimeline(dto.getTimeline());

        //setting profile
        UserProfile profile = userProfileService.fetchProfile(dto.getProfileId());
        education.setUserProfile(profile);

        education= educationRepository.save(education);

        aiService.embedEducationDocuments(education);

        return education;

    }

    @Transactional
    public UserEducation updateEducation(EducationUpdateDto educationUpdateDto){

        UserEducation education = educationRepository.findById(educationUpdateDto.getId())
                .orElseThrow(()->new IllegalArgumentException("Education data not found"));

        education.setTimeline(educationUpdateDto.getTimeline());
        education.setGrade(educationUpdateDto.getGrade());
        education.setQualification(educationUpdateDto.getQualification());

        education = educationRepository.save(education);

        aiService.updateEmbedEducationDocument(education);

        return education;
    }

    @Transactional
    public void deleteEducation(Long id){

        UserEducation education = educationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Education data not found"));

        educationRepository.delete(education);

        aiService.deleteEmbedEducationDocument(education);
    }

    public UserEducation getEducation(Long id){

        return educationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Education data not found"));

    }




}
