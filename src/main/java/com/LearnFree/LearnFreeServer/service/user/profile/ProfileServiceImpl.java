package com.LearnFree.LearnFreeServer.service.user.profile;

import com.LearnFree.LearnFreeServer.dto.PrincipalProfileUpdateDTO;
import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.dto.StudentProfileUpdateDTO;
import com.LearnFree.LearnFreeServer.dto.TeacherProfileUpdateDTO;
import com.LearnFree.LearnFreeServer.repository.UserAccountRepository;
import com.LearnFree.LearnFreeServer.repository.UserAuthenticationRepository;
import com.LearnFree.LearnFreeServer.entity.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserAccountRepository userAccountRepository;
    private final UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public ResponseDTO updateStudentProfile(String userEmail, StudentProfileUpdateDTO dto) {
        var userAuthentication = userAuthenticationRepository.findByEmail(userEmail);
        if (userAuthentication == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User not found")
                    .build();
        }
        var userAccount = userAccountRepository.findById(userAuthentication.getUserId()).orElse(null);
        if (userAccount == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User account not found")
                    .build();
        }

        userAccount.setFirstName(dto.getFirstName());
        userAccount.setLastName(dto.getLastName());
        userAccount.setGender(dto.getGender());
        userAccount.setDateOfBirth(dto.getDateOfBirth());
        userAccount.setMobileNumber(dto.getMobileNumber());
        userAccount.setDepartment(dto.getDepartment());
        userAccount.setPersonalEmail(dto.getPersonalEmail());
        userAccount.setRegistrationNumber(dto.getRegistrationNumber());
        userAccount.setGithub(dto.getGithub());
        userAccount.setLinkedIn(dto.getLinkedIn());

        userAccountRepository.save(userAccount);
        return ResponseDTO.builder()
                .status(true)
                .message("Profile updated successfully")
                .build();
    }

    @Override
    public ResponseDTO updateTeacherProfile(String userEmail, TeacherProfileUpdateDTO dto) {
        var userAuthentication = userAuthenticationRepository.findByEmail(userEmail);
        if (userAuthentication == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User not found")
                    .build();
        }
        var userAccount = userAccountRepository.findById(userAuthentication.getUserId()).orElse(null);
        if (userAccount == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User account not found")
                    .build();
        }

        userAccount.setFirstName(dto.getFirstName());
        userAccount.setLastName(dto.getLastName());
        userAccount.setGender(dto.getGender());
        userAccount.setDateOfBirth(dto.getDateOfBirth());
        userAccount.setMobileNumber(dto.getMobileNumber());
        userAccount.setDepartment(dto.getDepartment());
        userAccount.setPersonalEmail(dto.getPersonalEmail());

        userAccountRepository.save(userAccount);
        return ResponseDTO.builder()
                .status(true)
                .message("Profile updated successfully")
                .build();
    }

    @Override
    public ResponseDTO updatePrincipalProfile(String userEmail, PrincipalProfileUpdateDTO dto) {
        var userAuthentication = userAuthenticationRepository.findByEmail(userEmail);
        if (userAuthentication == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User not found")
                    .build();
        }
        var userAccount = userAccountRepository.findById(userAuthentication.getUserId()).orElse(null);
        if (userAccount == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User account not found")
                    .build();
        }

        userAccount.setFirstName(dto.getFirstName());
        userAccount.setLastName(dto.getLastName());
        userAccount.setGender(dto.getGender());
        userAccount.setDateOfBirth(dto.getDateOfBirth());
        userAccount.setMobileNumber(dto.getMobileNumber());
        userAccount.setDepartment(dto.getDepartment());
        userAccount.setPersonalEmail(dto.getPersonalEmail());
        userAccount.setQualifications(dto.getQualifications());

        userAccountRepository.save(userAccount);
        return ResponseDTO.builder()
                .status(true)
                .message("Profile updated successfully")
                .build();
    }

    @Override
    public Object fetchPublicProfile(Long userId) {
        var userAccount = userAccountRepository.findById(userId).orElse(null);
        if (userAccount == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User not found")
                    .build();
        }

        var userAuthentication = userAuthenticationRepository.findByUserId(userId);
        if (userAuthentication == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User authentication not found")
                    .build();
        }

        RoleEnum role = userAuthentication.getRole();
        return switch (role) {
            case STUDENT -> Map.of(
                    "name", userAccount.getFirstName() + " " + userAccount.getLastName(),
                    "registrationNumber", userAccount.getRegistrationNumber(),
                    "personalEmail", userAccount.getPersonalEmail(),
                    "github", userAccount.getGithub(),
                    "linkedIn", userAccount.getLinkedIn()
            );
            case STAFF, PRINCIPAL -> Map.of(
                    "name", userAccount.getFirstName() + " " + userAccount.getLastName(),
                    "phoneNumber", userAccount.getMobileNumber(),
                    "personalEmail", userAccount.getPersonalEmail()
            );
            default -> ResponseDTO.builder()
                    .status(false)
                    .message("Invalid role")
                    .build();
        };
    }
}