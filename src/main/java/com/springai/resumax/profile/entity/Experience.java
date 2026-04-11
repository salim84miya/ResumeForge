package com.springai.resumax.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String organization; // company / org
    private String timeline;

    @OneToMany(mappedBy = "experience")
    private List<ExperienceDescription> description;

    @ManyToOne
    private UserProfile userProfile;

}
