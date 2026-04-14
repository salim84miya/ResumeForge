package com.springai.resumax.profile.dto;


import lombok.Data;


@Data
public class UserExperienceInsertDto {

    private String organization; // company / org
    private String timeline;

    private String description;

    private Long profileId;

}
