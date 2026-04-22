package com.springai.resumax.profile.controller;

import com.springai.resumax.profile.dto.EducationInsertDto;
import com.springai.resumax.profile.dto.EducationUpdateDto;
import com.springai.resumax.profile.service.EducationService;
import com.springai.resumax.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class EducationController {


    private final EducationService educationService;


    @PostMapping("/education")
    public ResponseEntity<?> saveEducation(@RequestBody EducationInsertDto dto){

        var response = educationService.saveEducation(dto);

        return ResponseHandler.builder(response,null, HttpStatus.OK, LocalDateTime.now());
    }

    @PutMapping("/education/update")
    public ResponseEntity<?> updateEducation(@RequestBody EducationUpdateDto dto){

        var response = educationService.updateEducation(dto);

        return ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }

    @DeleteMapping("/education/delete/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable Long id){

        educationService.deleteEducation(id);

        return ResponseHandler.builder("deleted successfully",null,HttpStatus.OK,LocalDateTime.now());
    }

    @GetMapping("/education/fetch/{id}")
    public ResponseEntity<?> fetchEducation(@PathVariable Long id){

        var response = educationService.getEducation(id);

        return ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }
}
