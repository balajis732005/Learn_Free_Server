package com.LearnFree.LearnFreeServer.repository;

import com.LearnFree.LearnFreeServer.entity.GradeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeDataRepository extends JpaRepository<GradeData, Long> {
}
