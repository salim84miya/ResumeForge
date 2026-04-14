package com.springai.resumax.profile.entity;

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

    private String description;

    @ManyToOne
    private UserProfile userProfile;

}
