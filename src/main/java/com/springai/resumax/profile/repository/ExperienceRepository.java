package com.springai.resumax.profile.repository;

import com.springai.resumax.ai.entity.Experience;
import com.springai.resumax.profile.entity.UserExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<UserExperience,Long> {
}
