package com.springai.resumax.profile.dto;

import lombok.Data;

import java.util.List;

@Data
public class InsertSkillDto {

    private Long profileId;

    private List<String> skills;
}
