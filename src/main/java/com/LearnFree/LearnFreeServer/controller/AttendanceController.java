
package com.LearnFree.LearnFreeServer.controller;

import com.LearnFree.LearnFreeServer.dto.AttendanceRequestDTO;
import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.entity.Attendance;
import com.LearnFree.LearnFreeServer.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learn-free/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/submit")
    public ResponseEntity<ResponseDTO> submitAttendance(@RequestBody AttendanceRequestDTO request) {
        attendanceService.submitAttendance(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(true)
                .message("Attendance submitted successfully")
                .build());
    }

    @GetMapping("/history")
    public ResponseEntity<List<Attendance>> getAttendanceHistory(
            @RequestParam String department,
            @RequestParam Integer academicYear,
            @RequestParam Integer semester
    ) {
        return ResponseEntity.ok(attendanceService.getAttendanceHistory(department, academicYear, semester));
    }
}