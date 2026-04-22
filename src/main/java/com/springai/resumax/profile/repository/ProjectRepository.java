package com.springai.resumax.profile.repository;

import com.springai.resumax.profile.entity.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<UserProject,Long> {


    Optional<UserProject> findByName(String name);
}
