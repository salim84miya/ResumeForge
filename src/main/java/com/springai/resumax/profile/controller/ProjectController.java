package com.springai.resumax.profile.controller;

import com.springai.resumax.profile.dto.UserProjectInsertDto;
import com.springai.resumax.profile.dto.UserProjectUpdateDto;
import com.springai.resumax.profile.service.ProjectService;
import com.springai.resumax.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ProjectController {


    private final ProjectService projectService;


    @PostMapping("/project")
    public ResponseEntity<?> saveProfile(@RequestBody UserProjectInsertDto dto){

        var response = projectService.saveProject(dto);

        return ResponseHandler.builder(response,null, HttpStatus.OK, LocalDateTime.now());
    }

    @PutMapping("/project/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserProjectUpdateDto dto){

        var response = projectService.updateProject(dto);

        return ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }

    @DeleteMapping("/project/delete/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id){

        projectService.deleteProject(id);

        return ResponseHandler.builder("deleted Successfully",null,HttpStatus.OK,LocalDateTime.now());
    }

    @GetMapping("/project/fetch/{id}")
    public ResponseEntity<?> fetchProfile(@PathVariable Long id){

        var response = projectService.fetchProject(id);

        return ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }
}
