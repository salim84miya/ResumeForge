package com.springai.resumax.profile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"userProfile"})
public class UserProject {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String timeline; // "Jan 2025 – Mar 2025"

    @Column(columnDefinition = "TEXT")
    private String description;

    private String link;

    @JsonIgnore
    @ManyToOne
    private UserProfile userProfile;
}
