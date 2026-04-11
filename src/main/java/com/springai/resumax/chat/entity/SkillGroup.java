package com.springai.resumax.chat.entity;

import lombok.Data;

import java.util.List;

@Data
public class SkillGroup {

    private String category; // e.g. "Backend", "Database", "Tools"
    private List<String> skills;

}