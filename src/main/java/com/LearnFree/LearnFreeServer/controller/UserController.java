package com.LearnFree.LearnFreeServer.controller;

import com.LearnFree.LearnFreeServer.dto.*;
import com.LearnFree.LearnFreeServer.entity.RoleEnum;
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

    @PostMapping("/profile/update")
    public ResponseEntity<ResponseDTO> updateProfile(
            @RequestBody Object profileUpdateDTO,
            @AuthenticationPrincipal UserAuthentication userAuthentication
    ) {
        String userEmail = userAuthentication.getEmail();
        RoleEnum role = userAuthentication.getRole();

        ResponseDTO response;
        switch (role) {
            case STUDENT:
                response = profileService.updateStudentProfile(userEmail, (StudentProfileUpdateDTO) profileUpdateDTO);
                break;
            case STAFF:
                response = profileService.updateTeacherProfile(userEmail, (TeacherProfileUpdateDTO) profileUpdateDTO);
                break;
            case PRINCIPAL:
                response = profileService.updatePrincipalProfile(userEmail, (PrincipalProfileUpdateDTO) profileUpdateDTO);
                break;
            default:
                return ResponseEntity.badRequest().body(ResponseDTO.builder()
                        .status(false)
                        .message("Invalid role")
                        .build());
        }

        return ResponseEntity.ok(response);
    }
    @GetMapping("/profile/view/{userId}")
    public ResponseEntity<Object> fetchProfile(
            @PathVariable Long userId
    ) {
            return ResponseEntity.ok(profileService.fetchPublicProfile(userId));
    }

}

