package com.springai.resumax.profile.repository;

import com.springai.resumax.profile.entity.UserExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<UserExperience,Long> {


    Optional<UserExperience> findByDescriptionAndOrganization(String description,String organization);
}
