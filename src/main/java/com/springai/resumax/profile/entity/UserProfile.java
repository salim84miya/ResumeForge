package com.springai.resumax.profile.entity;

import com.springai.resumax.security.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String linkedIn;
    private String location;
    private String summary;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "userProfile")
    private List<UserSkill> skills;

    @OneToMany(mappedBy = "userProfile")
    private List<UserEducation> userEducation;

    @OneToMany(mappedBy = "userProfile")
    private List<UserProject> userProjects;

    @OneToMany(mappedBy = "userProfile")
    private List<UserExperience> userExperiences;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
