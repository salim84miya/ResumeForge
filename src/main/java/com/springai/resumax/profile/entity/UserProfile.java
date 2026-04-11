package com.springai.resumax.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String name;
    private String email;
    private String linkedIn;
    private String location;
    private String summary;

    @OneToOne(mappedBy = "userProfile")
    private Education education;

    @OneToMany(mappedBy = "userProfile")
    private List<SkillGroup> skillGroups;

    @OneToMany(mappedBy = "userProfile")
    private List<Project> projects;

    @OneToMany(mappedBy = "userProfile")
    private List<Experience> experiences;

}
