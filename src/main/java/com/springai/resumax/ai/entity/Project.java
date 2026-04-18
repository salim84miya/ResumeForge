package com.springai.resumax.ai.entity;

import lombok.Data;

import java.util.List;

@Data
public class Project {

    private String name;
    private String timeline; // "Jan 2025 – Mar 2025"

    private List<String> description;

    private String link;
}
