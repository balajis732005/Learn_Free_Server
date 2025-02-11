package com.LearnFree.LearnFreeServer.controller;

import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.entity.UserAccount;
import com.LearnFree.LearnFreeServer.repository.UserAccountRepository;
import com.LearnFree.LearnFreeServer.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/learn-free")
@RequiredArgsConstructor

public class StaffController {

    private final StaffService staffService;
    private final UserAccountRepository userAccountRepository;

    @PostMapping("/staff/add-students")
    public ResponseEntity<ResponseDTO> readOperationExcel(
            @RequestParam("students_data") MultipartFile students_data,
            @RequestParam("department") String department
    ){
        return ResponseEntity.ok(staffService.addStudents(students_data,department));
    }

    @GetMapping("/staff/students")
    public ResponseEntity<List<UserAccount>> getStudentsByDepartment(@RequestParam String department) {
        List<UserAccount> students = userAccountRepository.findByDepartment(department);

        if (students == null || students.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Ensure an empty array is returned instead of null
        }

        return ResponseEntity.ok(students);
    }


}
