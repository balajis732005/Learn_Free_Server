package com.LearnFree.LearnFreeServer.repository;

import com.LearnFree.LearnFreeServer.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {

    UserAuthentication findByEmail(String email);

    boolean existsByEmail(String email);
    Optional<UserAuthentication> findByUserId(Long userId);

}