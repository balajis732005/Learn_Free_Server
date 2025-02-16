package com.LearnFree.LearnFreeServer.controller;

import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.service.grade.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/learn-free")
@RequiredArgsConstructor

public class GradeController {

    private final GradeService gradeService;

    public ResponseEntity<ResponseDTO> addStudentsGrade(
            @RequestParam("students_grade") MultipartFile students_grade,
            @RequestParam("department") String department,
            @RequestParam("exam_name") String exam_name,
            @RequestParam("subject_name") String subject_name
    ){
        return ResponseEntity.ok(gradeService.studentsGradeAdd(students_grade,exam_name,subject_name,department));
    }

}
