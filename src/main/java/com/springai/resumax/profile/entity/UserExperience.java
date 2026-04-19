package com.springai.resumax.profile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String organization; // company / org
    private String timeline;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToOne
    private UserProfile userProfile;

}
