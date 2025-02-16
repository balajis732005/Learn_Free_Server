package com.LearnFree.LearnFreeServer.repository;

import com.LearnFree.LearnFreeServer.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findById(Long id);
    List<UserAccount> findByDepartment(String department);
    List<UserAccount> findByDepartmentAndAcademicYear(String department, Integer academicYear);

    UserAccount findByRegistrationNumber(String regNum);

}