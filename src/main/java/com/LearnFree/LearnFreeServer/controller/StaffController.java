package com.LearnFree.LearnFreeServer.controller;

import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/learn-free")
@RequiredArgsConstructor

public class StaffController {

    private final StaffService staffService;

    @PostMapping("/staff/add-students")
    public ResponseEntity<ResponseDTO> readOperationExcel(
            @RequestParam("students_data") MultipartFile students_data,
            @RequestParam("department") String department
    ){
        return ResponseEntity.ok(staffService.addStudents(students_data,department));
    }

}
