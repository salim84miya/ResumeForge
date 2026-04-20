package com.springai.resumax.profile.controller;

import com.springai.resumax.profile.dto.*;
import com.springai.resumax.profile.entity.*;
import com.springai.resumax.profile.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {


    private final UserProfileService profileService;
    private final ProjectService projectService;
    private final ExperienceService experienceService;
    private final EducationService educationService;
    private final UserSkillService skillService;

    @PostMapping("/profile")
    public ResponseEntity<UserProfile> saveProfile(@RequestBody UserProfileInsertDto profileInsertDto){

        var response = profileService.saveProfile(profileInsertDto);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/skill")
    public ResponseEntity<List<UserSkill>> saveSkills(@RequestBody InsertSkillDto dto){

        var response = skillService.saveMultipleSkill(dto);

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
