package com.springai.resumax.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qualification; // company / org
    private String timeline;
    private String grade;

    @OneToOne
    private UserProfile userProfile;
}
