package com.springai.resumax.profile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"userProfile"})
public class UserEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qualification; // company / org
    private String timeline;
    private String grade;

    @JsonIgnore
    @ManyToOne
    private UserProfile userProfile;
}
