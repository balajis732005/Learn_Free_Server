package com.LearnFree.LearnFreeServer.service.staff;

import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StaffService {
    ResponseDTO addStudents(MultipartFile file, String department, Integer academicYear);
}
