package com.springai.resumax.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ExperienceDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    private Experience experience;
}
