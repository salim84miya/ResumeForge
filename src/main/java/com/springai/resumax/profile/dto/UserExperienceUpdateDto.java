package com.springai.resumax.profile.dto;


import lombok.Data;


@Data
public class UserExperienceUpdateDto {

    private Long id;
    private String organization; // company / org
    private String timeline;

    private String description;

    private Long profileId;

}
