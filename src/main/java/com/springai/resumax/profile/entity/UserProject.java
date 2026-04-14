package com.springai.resumax.profile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UserProject {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String timeline; // "Jan 2025 – Mar 2025"

    private String description;

    private String link;

    @ManyToOne
    private UserProfile userProfile;
}
