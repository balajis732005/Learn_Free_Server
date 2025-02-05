package com.LearnFree.LearnFreeServer.controller;

import com.LearnFree.LearnFreeServer.dto.AuthenticationRequestDTO;
import com.LearnFree.LearnFreeServer.dto.AuthenticationResponseDTO;
import com.LearnFree.LearnFreeServer.dto.RegistrationRequestDTO;
import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import com.LearnFree.LearnFreeServer.service.user.authentication.AuthenticationService;
import com.LearnFree.LearnFreeServer.service.user.registration.RegistrationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learn-free")
@RequiredArgsConstructor

public class UserController {

    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;

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

}

