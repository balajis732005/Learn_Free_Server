package com.LearnFree.LearnFreeServer.service.grade;

import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface GradeService {

    ResponseDTO studentsGradeAdd(MultipartFile gradeSheet,String exam_name,String subject_name,String department);

}
