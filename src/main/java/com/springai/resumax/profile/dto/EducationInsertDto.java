package com.springai.resumax.profile.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class EducationInsertDto {

    @NotBlank
    private String qualification; // company / org
    private String timeline;
    private String grade;
    private Long profileId;
}
