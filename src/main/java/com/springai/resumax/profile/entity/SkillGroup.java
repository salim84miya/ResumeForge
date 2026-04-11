package com.springai.resumax.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class SkillGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category; // e.g. "Backend", "Database", "Tools"

    @OneToMany(mappedBy = "skillGroup")
    private List<Skill> skills;

    @ManyToOne
    private UserProfile userProfile;

}