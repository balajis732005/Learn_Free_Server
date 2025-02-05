package com.LearnFree.LearnFreeServer.repository;

import com.LearnFree.LearnFreeServer.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {

    UserAuthentication findByEmail(String email);

}
