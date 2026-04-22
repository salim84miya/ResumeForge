package com.springai.resumax.profile.controller;

import com.springai.resumax.profile.dto.UserExperienceInsertDto;
import com.springai.resumax.profile.dto.UserExperienceUpdateDto;
import com.springai.resumax.profile.service.ExperienceService;
import com.springai.resumax.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping("/experience")
    public ResponseEntity<?> saveExperience(@RequestBody UserExperienceInsertDto dto){

        var response = experienceService.saveExperience(dto);

        return ResponseHandler.builder(response,null, HttpStatus.OK, LocalDateTime.now());
    }

    @PutMapping("/experience/update")
    public ResponseEntity<?> updateExperience(@RequestBody UserExperienceUpdateDto dto){

        var response = experienceService.updateExperience(dto);

        return ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }

    @DeleteMapping("/experience/delete/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long id){

        experienceService.deleteExperience(id);

        return ResponseHandler.builder("deleted Successfully",null,HttpStatus.OK,LocalDateTime.now());
    }

    @GetMapping("/experience/fetch/{id}")
    public ResponseEntity<?> fetchExperience(@PathVariable Long id){

        var response = experienceService.fetchExperience(id);

        return  ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }
}
