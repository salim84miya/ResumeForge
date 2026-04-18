package com.springai.resumax.profile.dto;

import lombok.Data;


@Data
public class EducationInsertDto {

    private String qualification; // company / org
    private String timeline;
    private String grade;
    private Long profileId;
}
