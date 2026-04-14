package com.springai.resumax.profile.repository;

import com.springai.resumax.profile.entity.UserEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<UserEducation,Long> {
}
