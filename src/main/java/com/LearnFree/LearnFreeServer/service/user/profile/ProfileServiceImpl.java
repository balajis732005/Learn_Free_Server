package com.LearnFree.LearnFreeServer.service.user.profile;

import com.LearnFree.LearnFreeServer.dto.PrincipalProfileUpdateDTO;
import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.dto.StudentProfileUpdateDTO;
import com.LearnFree.LearnFreeServer.dto.TeacherProfileUpdateDTO;
import com.LearnFree.LearnFreeServer.entity.RoleEnum;
import com.LearnFree.LearnFreeServer.entity.UserAccount;
import com.LearnFree.LearnFreeServer.repository.UserAccountRepository;
import com.LearnFree.LearnFreeServer.repository.UserAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserAccountRepository userAccountRepository;
    private final UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public ResponseDTO updateStudentProfile(String userEmail, StudentProfileUpdateDTO dto) {
        var userAuthentication = userAuthenticationRepository.findByEmail(userEmail);
        if (userAuthentication == null || !userAuthentication.getRole().equals(RoleEnum.STUDENT)) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("Unauthorized access: Only students can update their profiles.")
                    .build();
        }

        var userAccount = userAccountRepository.findById(userAuthentication.getUserId()).orElse(null);
        if (userAccount == null) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User account not found.")
                    .build();
        }

        if (dto.getFirstName() != null) userAccount.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) userAccount.setLastName(dto.getLastName());
        if (dto.getGender() != null) userAccount.setGender(dto.getGender());
        if (dto.getDateOfBirth() != null) userAccount.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getMobileNumber() != null) userAccount.setMobileNumber(dto.getMobileNumber());
        if (dto.getDepartment() != null) userAccount.setDepartment(dto.getDepartment());
        if (dto.getPersonalEmail() != null) userAccount.setPersonalEmail(dto.getPersonalEmail());
        if (dto.getGithub() != null) userAccount.setGithub(dto.getGithub());
        if (dto.getLinkedIn() != null) userAccount.setLinkedIn(dto.getLinkedIn());

        userAccountRepository.save(userAccount);

        return ResponseDTO.builder()
                .status(true)
                .message("Profile updated successfully.")
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
        var userAccountOptional = userAccountRepository.findById(userId);
        if (userAccountOptional.isEmpty()) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User not found")
                    .build();
        }
        var userAccount = userAccountOptional.get();

        var userAuthenticationOptional = userAuthenticationRepository.findByUserId(userId);
        if (userAuthenticationOptional.isEmpty()) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("User authentication not found")
                    .build();
        }
        var userAuthentication = userAuthenticationOptional.get();

        RoleEnum role = userAuthentication.getRole();

        return switch (role) {
            case STUDENT -> buildStudentProfile(userAccount);
            case STAFF, PRINCIPAL -> buildTeacherProfile(userAccount);
            default -> ResponseDTO.builder()
                    .status(false)
                    .message("Invalid role")
                    .build();
        };
    }

    private Map<String, String> buildStudentProfile(UserAccount userAccount) {
        Map<String, String> profile = new HashMap<>();
        profile.put("name", userAccount.getFirstName() + " " + userAccount.getLastName());
        if (userAccount.getPersonalEmail() != null) profile.put("personalEmail", userAccount.getPersonalEmail());
        if (userAccount.getDepartment() != null) profile.put("department", userAccount.getDepartment());
        if (userAccount.getGithub() != null) profile.put("github", userAccount.getGithub());
        if (userAccount.getLinkedIn() != null) profile.put("linkedIn", userAccount.getLinkedIn());
        return profile;
    }

    private Map<String, String> buildTeacherProfile(UserAccount userAccount) {
        Map<String, String> profile = new HashMap<>();
        profile.put("name", userAccount.getFirstName() + " " + userAccount.getLastName());
        if (userAccount.getPersonalEmail() != null) profile.put("personalEmail", userAccount.getPersonalEmail());
        if (userAccount.getMobileNumber() != null) profile.put("phoneNumber", userAccount.getMobileNumber());
        return profile;
    }
}