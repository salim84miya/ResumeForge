package com.springai.resumax.profile.repository;

import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill,Long> {


    List<UserSkill> findAllByUserProfile(UserProfile userProfile);

}
