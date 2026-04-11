package com.springai.resumax.chat.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserProfile {

    private String userId;
    private String name;
    private String email;
    private String linkedIn;
    private String location;
    private String summary;
    private Education education;

    private List<SkillGroup> skillGroups;
    private List<Project> projects;
    private List<Experience> experiences;

}
