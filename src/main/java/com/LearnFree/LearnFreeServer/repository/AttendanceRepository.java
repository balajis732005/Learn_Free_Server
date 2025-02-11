package com.LearnFree.LearnFreeServer.repository;

import com.LearnFree.LearnFreeServer.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserIdAndDepartmentAndSemester(Long userId, String department, String semester);
    List<Attendance> findByDepartmentAndSemesterAndDate(String department, String semester, LocalDate date);
}