package com.LearnFree.LearnFreeServer.repository;

import com.LearnFree.LearnFreeServer.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
}
