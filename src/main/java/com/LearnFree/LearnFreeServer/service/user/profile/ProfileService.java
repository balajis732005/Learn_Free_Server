package com.LearnFree.LearnFreeServer.service.user.profile;

import com.LearnFree.LearnFreeServer.dto.PrincipalProfileUpdateDTO;
import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.dto.StudentProfileUpdateDTO;
import com.LearnFree.LearnFreeServer.dto.TeacherProfileUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    ResponseDTO updateStudentProfile(String userEmail, StudentProfileUpdateDTO studentProfileUpdateDTO);
    ResponseDTO updateTeacherProfile(String userEmail, TeacherProfileUpdateDTO teacherProfileUpdateDTO);
    ResponseDTO updatePrincipalProfile(String userEmail, PrincipalProfileUpdateDTO principalProfileUpdateDTO);
    Object fetchPublicProfile(Long userId);
}
