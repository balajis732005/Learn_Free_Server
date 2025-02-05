package com.LearnFree.LearnFreeServer.service.user.registration;

import com.LearnFree.LearnFreeServer.dto.RegistrationRequestDTO;
import com.LearnFree.LearnFreeServer.dto.ResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

    ResponseDTO registrationEmailValidation(String email) throws MessagingException;

    ResponseDTO userRegistration(RegistrationRequestDTO registrationRequestDTO)
            throws MessagingException;

}
