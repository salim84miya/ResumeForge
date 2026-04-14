package com.springai.resumax.profile.dto;

import lombok.Data;


@Data
public class EducationUpdateDto {

    private Long id;
    private String qualification; // company / org
    private String timeline;
    private String grade;
}
