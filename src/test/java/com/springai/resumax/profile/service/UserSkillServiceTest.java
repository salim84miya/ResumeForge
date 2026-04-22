package com.springai.resumax.profile.service;

import com.springai.resumax.ai.service.AiService;
import com.springai.resumax.profile.dto.InsertSkillDto;
import com.springai.resumax.profile.dto.RemoveSkillDto;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserSkill;
import com.springai.resumax.profile.repository.UserSkillRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserSkillServiceTest {

    @Mock
    private UserSkillRepository skillRepository;

    @InjectMocks
    private UserSkillService skillService;

    @Mock
    private AiService aiService;

    @Mock
    private UserProfileService profileService;

    private List<UserSkill> skills;
    private InsertSkillDto insertDto;
    private RemoveSkillDto removeDto;
    private UserProfile userProfile;

    @BeforeEach
    void setUp() {

        userProfile = new UserProfile();
        userProfile.setUserId("1");
        userProfile.setName("sam turner");
        userProfile.setEmail("sam@gmial.com");
        userProfile.setLocation("random location");
        userProfile.setSummary("this is an random summary");
        userProfile.setLinkedIn("https://sam.linkedin.com");

        insertDto = new InsertSkillDto();

        insertDto.setSkills(List.of("java","spring boot","postgres"));
        insertDto.setProfileId(1L);

        removeDto = new RemoveSkillDto();

        removeDto.setSkills(List.of("java"));
        removeDto.setProfileId(1L);

        skills = new ArrayList<>();

        UserSkill skill1 = new UserSkill();

        skill1.setId(1L);
        skill1.setUserProfile(userProfile);
        skill1.setSkill("java");

        UserSkill skill2 = new UserSkill();

        skill2.setId(2L);
        skill2.setUserProfile(userProfile);
        skill2.setSkill("spring boot");

        skills.add(skill1);
        skills.add(skill2);

    }

    @AfterEach
    void tearDown() {

        userProfile = null;
        skills = null;
        insertDto = null;
        removeDto = null;
    }

    @Test
    void test_saveMultipleSkillSuccessfully() {

        Mockito.when(profileService.fetchProfile(insertDto.getProfileId())).thenReturn(userProfile);
        Mockito.when(skillRepository.findAllByUserProfile(userProfile)).thenReturn(null);
        Mockito.when(skillRepository.saveAll(ArgumentMatchers.any(List.class))).thenReturn(skills);

        List<UserSkill> newSkills =  skillService.saveMultipleSkill(insertDto);

        assertEquals(insertDto.getSkills().get(0),newSkills.get(0).getSkill());
        assertEquals(insertDto.getSkills().get(1),newSkills.get(1).getSkill());

        verify(aiService,times(1)).embedSkillsDocuments(String.join(",",insertDto.getSkills()), userProfile.getUserId());
    }

    @Test
    void removeSingleSkill() {
        Mockito.when(profileService.fetchProfile(removeDto.getProfileId())).thenReturn(userProfile);
        Mockito.when(skillRepository.findAllByUserProfile(userProfile)).thenReturn(skills);

        Mockito.when(skillRepository.saveAll(ArgumentMatchers.any(List.class))).thenReturn(skills);

        List<UserSkill> updatedSkills = skillService.removeSingleSkill(removeDto);

        List<String> userSkills = updatedSkills.stream().map(UserSkill::getSkill).toList();

        verify(aiService,times(1)).deleteEmbedSkillsDocuments(userProfile.getUserId());
        verify(aiService,times(1)).embedSkillsDocuments(String.join(",",userSkills),userProfile.getUserId());
    }
}