package com.springai.resumax.profile.dto;

import lombok.Data;

@Data
public class UserProjectInsertDto {


    private String name;
    private String timeline; // "Jan 2025 – Mar 2025"

    private String description;

    private String link;

    private Long profileId;
}
