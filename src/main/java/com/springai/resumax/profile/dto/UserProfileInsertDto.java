package com.springai.resumax.profile.dto;

import lombok.Data;

@Data
public class UserProfileInsertDto {

    private String userId;
    private String name;
    private String email;
    private String linkedIn;
    private String location;
    private String summary;
    private String skills;

}
