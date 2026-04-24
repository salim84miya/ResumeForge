package com.springai.resumax.profile.repository;

import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

    Optional<UserProfile> findByUser(User user);
}
