package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.UserProfileInsertDto;
import com.springai.resumax.profile.dto.UserProfileUpdateDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;

    private final AiService aiService;

    @Transactional
    public UserProfile saveProfile(UserProfileInsertDto dto){

        UserProfile userProfile = new UserProfile();

        userProfile.setUserId(dto.getUserId());
        userProfile.setName(dto.getName());
        userProfile.setEmail(dto.getEmail());
        userProfile.setLocation(dto.getLocation());
        userProfile.setSummary(dto.getSummary());
        userProfile.setLinkedIn(dto.getLinkedIn());


        userProfile =  repository.save(userProfile);

        aiService.embedProfileDocuments(userProfile);
        aiService.embedSummaryDocuments(userProfile);

        return userProfile;
    }

    @Transactional
    public UserProfile updateProfile(UserProfileUpdateDto dto){

        UserProfile userProfile = repository.findById(dto.getId())
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        userProfile.setUserId(dto.getUserId());
        userProfile.setName(dto.getName());
        userProfile.setEmail(dto.getEmail());
        userProfile.setLocation(dto.getLocation());
        userProfile.setSummary(dto.getSummary());
        userProfile.setLinkedIn(dto.getLinkedIn());

        return  repository.save(userProfile);
    }

    @Transactional
    public void deleteProfile(Long id){

        UserProfile userProfile = repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        repository.delete(userProfile);

//        return  userProfile;
    }


    public UserProfile fetchProfile(Long id){

        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));
    }


}
