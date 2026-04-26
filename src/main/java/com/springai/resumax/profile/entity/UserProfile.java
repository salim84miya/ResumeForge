package com.springai.resumax.profile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springai.resumax.security.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"skills","userEducation","userProjects","userExperiences"})
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String linkedIn;
    private String location;
    private String summary;

    @JsonIgnore
    @OneToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserSkill> skills;

    @JsonIgnore
    @OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserEducation> userEducation;

    @JsonIgnore
    @OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserProject> userProjects;

    @JsonIgnore
    @OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserExperience> userExperiences;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
