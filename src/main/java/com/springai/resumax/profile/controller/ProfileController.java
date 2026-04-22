package com.springai.resumax.profile.controller;

import com.springai.resumax.profile.dto.*;
import com.springai.resumax.profile.entity.*;
import com.springai.resumax.profile.service.*;
import com.springai.resumax.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ProfileController {


    private final UserProfileService profileService;

    @PostMapping("/profile")
    public ResponseEntity<?> saveProfile(@RequestBody UserProfileInsertDto profileInsertDto){

        var response = profileService.saveProfile(profileInsertDto);

        return ResponseHandler.builder(response,null, HttpStatus.OK, LocalDateTime.now());
    }


    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileUpdateDto profileUpdateDto){

        var response = profileService.updateProfile(profileUpdateDto);

        return ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }

    @DeleteMapping("/profile/delete/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id){

        profileService.deleteProfile(id);

        return ResponseHandler.builder("deleted Successfully",null,HttpStatus.OK,LocalDateTime.now());
    }

    @GetMapping("/profile/fetch/{id}")
    public ResponseEntity<?> fetchProfile(@PathVariable Long id){

        var response = profileService.fetchProfile(id);

        return ResponseHandler.builder(response,null,HttpStatus.OK,LocalDateTime.now());
    }

}
