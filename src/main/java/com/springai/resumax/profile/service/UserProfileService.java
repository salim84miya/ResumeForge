package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.UserProfileInsertDto;
import com.springai.resumax.profile.dto.UserProfileUpdateDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.repository.UserProfileRepository;
import com.springai.resumax.security.entity.User;
import com.springai.resumax.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserProfileRepository repository;

    private final UserRepository userRepository;

    private final AiService aiService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public UserProfile saveProfile(UserProfileInsertDto dto){

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->
                new IllegalArgumentException("no user found"));


         Optional<UserProfile> profile = repository.findByUser(user);

        if( profile.isPresent()){
            throw new IllegalArgumentException("profile already exists!");
        }

        UserProfile userProfile = new UserProfile();

        userProfile.setName(dto.getName());
        userProfile.setEmail(dto.getEmail());
        userProfile.setLocation(dto.getLocation());
        userProfile.setSummary(dto.getSummary());
        userProfile.setLinkedIn(dto.getLinkedIn());
        userProfile.setUser(user);

        userProfile =  repository.save(userProfile);

        user.setUserProfile(userProfile);

        userRepository.save(user);

        aiService.embedProfileDocuments(userProfile);
        aiService.embedSummaryDocuments(userProfile);

        return userProfile;
    }

    @Transactional
    public UserProfile updateProfile(UserProfileUpdateDto dto){

        UserProfile userProfile = repository.findById(dto.getId())
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        userProfile.setName(dto.getName());
        userProfile.setEmail(dto.getEmail());
        userProfile.setLocation(dto.getLocation());
        userProfile.setSummary(dto.getSummary());
        userProfile.setLinkedIn(dto.getLinkedIn());

        userProfile =  repository.save(userProfile);

        aiService.updateEmbedProfileDocuments(userProfile);
        aiService.updateEmbedSummaryDocuments(userProfile);

        return  userProfile;
    }

    @Transactional
    public void deleteProfile(Long id){

        UserProfile userProfile = repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));

        userProfile.getUser().setUserProfile(null);

        logger.info("called delete profile found {}",userProfile.getId());

        repository.delete(userProfile);

        logger.info("delete user profile from db successfully");


        logger.info("called embed profile document");

        aiService.deleteEmbedProfileDocuments(userProfile);
        aiService.deleteEmbedSummaryDocuments(userProfile);
    }


    public UserProfile fetchProfile(Long id){

        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("no data found"));
    }


}
