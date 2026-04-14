package com.springai.resumax.profile.service;

import com.springai.resumax.profile.dto.EducationInsertDto;
import com.springai.resumax.profile.dto.EducationUpdateDto;
import com.springai.resumax.profile.entity.UserEducation;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.EducationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserProfileService userProfileService;

    @Transactional
    public UserEducation saveEducation(EducationInsertDto dto){

        UserEducation education = new UserEducation();

        education.setGrade(dto.getGrade());
        education.setQualification(dto.getQualification());
        education.setTimeline(dto.getTimeline());

        //setting profile
        UserProfile profile = userProfileService.fetchProfile(dto.getProfileId());
        education.setUserProfile(profile);

        return educationRepository.save(education);

    }

    @Transactional
    public UserEducation updateEducation(EducationUpdateDto educationUpdateDto){

        UserEducation education = educationRepository.findById(educationUpdateDto.getId())
                .orElseThrow(()->new IllegalArgumentException("Education data not found"));

        education.setTimeline(educationUpdateDto.getTimeline());
        education.setGrade(educationUpdateDto.getGrade());
        education.setQualification(educationUpdateDto.getQualification());

        return educationRepository.save(education);
    }

    @Transactional
    public UserEducation deleteEducation(Long id){

        UserEducation education = educationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Education data not found"));

        educationRepository.delete(education);

        return  education;
    }

    public UserEducation getEducation(Long id){

        return educationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Education data not found"));

    }




}
