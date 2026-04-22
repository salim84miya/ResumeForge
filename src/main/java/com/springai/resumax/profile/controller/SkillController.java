package com.springai.resumax.profile.controller;

import com.springai.resumax.profile.dto.InsertSkillDto;
import com.springai.resumax.profile.dto.RemoveSkillDto;
import com.springai.resumax.profile.service.UserSkillService;
import com.springai.resumax.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class SkillController {

    private final UserSkillService skillService;

    @PostMapping("/skill")
    public ResponseEntity<?> saveSkill(@RequestBody InsertSkillDto dto){

        var response = skillService.saveMultipleSkill(dto);

        return ResponseHandler.builder(response,null, HttpStatus.OK, LocalDateTime.now());
    }

    @DeleteMapping("/skill/remove")
    public ResponseEntity<?> removeSkills(@RequestBody RemoveSkillDto dto){

        var response = skillService.removeSingleSkill(dto);

        return ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }
}
