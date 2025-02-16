package com.LearnFree.LearnFreeServer.service.grade;

import com.LearnFree.LearnFreeServer.entity.Grade;
import com.LearnFree.LearnFreeServer.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    public double calculateCGPA(Long studentId) {
        List<Grade> grades = gradeRepository.findByStudentId(studentId);
        return grades.stream()
                .mapToDouble(g -> convertGradeToPoints(g.getGrade()))
                .average()
                .orElse(0.0);
    }

    private double convertGradeToPoints(String grade) {
        return switch (grade) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            default -> 0.0;
        };
    }
}