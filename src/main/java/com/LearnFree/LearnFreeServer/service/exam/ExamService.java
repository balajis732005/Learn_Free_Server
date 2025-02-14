package com.LearnFree.LearnFreeServer.service.exam;

import com.LearnFree.LearnFreeServer.entity.Exam;

import java.util.List;

public interface ExamService {
    List<Exam> getExams(String department, String semester);
    Exam addExam(Exam exam);
    void deleteExam(Long id);
}