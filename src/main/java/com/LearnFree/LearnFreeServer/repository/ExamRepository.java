package com.LearnFree.LearnFreeServer.repository;

import com.LearnFree.LearnFreeServer.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByDepartmentAndSemester(String department, String semester);
    List<Exam> findByDepartment(String department);
    List<Exam> findBySemester(String semester);
}