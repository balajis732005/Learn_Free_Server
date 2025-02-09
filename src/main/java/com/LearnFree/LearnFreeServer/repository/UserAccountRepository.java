package com.LearnFree.LearnFreeServer.repository;

import com.LearnFree.LearnFreeServer.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findById(Long id);
}