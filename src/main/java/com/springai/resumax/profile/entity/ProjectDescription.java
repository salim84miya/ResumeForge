package com.springai.resumax.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProjectDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    private Project project;
}
