package com.springai.resumax.ai.entity;

import lombok.Data;

import java.util.List;

@Data
public class JobDetails {

    private String role;
    private List<String> requiredSkills;
    private List<String> responsibilities;
    private List<String> keywords;

}
