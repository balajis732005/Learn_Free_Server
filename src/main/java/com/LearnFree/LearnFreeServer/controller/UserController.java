package com.LearnFree.LearnFreeServer.controller;

import com.LearnFree.LearnFreeServer.dto.*;
import com.LearnFree.LearnFreeServer.entity.UserAuthentication;
import com.LearnFree.LearnFreeServer.service.user.authentication.AuthenticationService;
import com.LearnFree.LearnFreeServer.service.user.profile.ProfileService;
import com.LearnFree.LearnFreeServer.service.user.registration.RegistrationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learn-free")
@RequiredArgsConstructor

public class UserController {

    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;
    private final ProfileService profileService;

    @GetMapping("/registration/email-validation/{email}")
    public ResponseEntity<ResponseDTO> registrationEmailValidation(
            @PathVariable String email
    ) throws MessagingException {
        return ResponseEntity.ok(registrationService.registrationEmailValidation(email));
    }

    @PostMapping("/registration/verify")
    public ResponseEntity<ResponseDTO> verifyActivationCode(
            @RequestBody RegistrationRequestDTO registrationRequestDTO
    ) throws MessagingException {
        return ResponseEntity.ok(registrationService.userRegistration(registrationRequestDTO));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO authenticationRequestDTO
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }

    @PostMapping("/profile/student/update")
    public ResponseEntity<ResponseDTO> updateStudentProfile(
            @RequestBody StudentProfileUpdateDTO studentProfileUpdateDTO,
            @AuthenticationPrincipal UserAuthentication userAuthentication
    ) {
        String userEmail = userAuthentication.getEmail();
        ResponseDTO response = profileService.updateStudentProfile(userEmail, studentProfileUpdateDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile/teacher/update")
    public ResponseEntity updateTeacherProfile(
            @RequestBody TeacherProfileUpdateDTO teacherProfileUpdateDTO,
            @AuthenticationPrincipal UserAuthentication userAuthentication
    ) {
        String userEmail = userAuthentication.getEmail();
        ResponseDTO response = profileService.updateTeacherProfile(userEmail, teacherProfileUpdateDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile/principal/update")
    public ResponseEntity updatePrincipalProfile(
            @RequestBody PrincipalProfileUpdateDTO principalProfileUpdateDTO,
            @AuthenticationPrincipal UserAuthentication userAuthentication
    ) {
        String userEmail = userAuthentication.getEmail();
        ResponseDTO response = profileService.updatePrincipalProfile(userEmail, principalProfileUpdateDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/profile/view/{userId}")
    public ResponseEntity<Object> fetchProfile(
            @PathVariable Long userId
    ) {
            return ResponseEntity.ok(profileService.fetchPublicProfile(userId));
    }

}
