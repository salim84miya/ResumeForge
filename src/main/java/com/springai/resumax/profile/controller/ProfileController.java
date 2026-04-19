package com.springai.resumax.profile.controller;

import com.springai.resumax.profile.dto.EducationInsertDto;
import com.springai.resumax.profile.dto.UserExperienceInsertDto;
import com.springai.resumax.profile.dto.UserProfileInsertDto;
import com.springai.resumax.profile.dto.UserProjectInsertDto;
import com.springai.resumax.profile.entity.UserEducation;
import com.springai.resumax.profile.entity.UserExperience;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserProject;
import com.springai.resumax.profile.service.EducationService;
import com.springai.resumax.profile.service.ExperienceService;
import com.springai.resumax.profile.service.ProjectService;
import com.springai.resumax.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {


    private final UserProfileService profileService;
    private final ProjectService projectService;
    private final ExperienceService experienceService;
    private final EducationService educationService;


    @PostMapping("/profile")
    public ResponseEntity<UserProfile> saveProfile(@RequestBody UserProfileInsertDto profileInsertDto){

        var response = profileService.saveProfile(profileInsertDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/project")
    public ResponseEntity<UserProject> saveProject(@RequestBody UserProjectInsertDto projectInsertDto){

        var response = projectService.saveProject(projectInsertDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/experience")
    public ResponseEntity<UserExperience> saveExperience(@RequestBody UserExperienceInsertDto insertDto){

        var response = experienceService.saveExperience(insertDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/education")
    public ResponseEntity<UserEducation> saveEducation(@RequestBody EducationInsertDto educationInsertDto){

        var response = educationService.saveEducation(educationInsertDto);

        return ResponseEntity.ok(response);
    }


}
