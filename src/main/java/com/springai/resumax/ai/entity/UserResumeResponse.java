package com.springai.resumax.ai.entity;

import lombok.Data;
import java.util.List;


@Data
public class UserResumeResponse {

    private String userId;
    private String name;
    private String email;
    private String linkedIn;
    private String location;
    private String summary;

    private List<Education> education;

    private List<SkillGroup> skillGroups;

    private List<Project> projects;

    private List<Experience> experiences;

    private Double matchingScore;

    private String missingSkills;

}
