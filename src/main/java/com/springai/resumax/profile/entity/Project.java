package com.springai.resumax.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String timeline; // "Jan 2025 – Mar 2025"

    @OneToMany(mappedBy = "project")
    private List<ProjectDescription> description;

    @ManyToOne
    private UserProfile userProfile;

    private String link;

}
