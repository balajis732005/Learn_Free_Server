package com.LearnFree.LearnFreeServer.service.user.authentication;

import com.LearnFree.LearnFreeServer.dto.AuthenticationRequestDTO;
import com.LearnFree.LearnFreeServer.dto.AuthenticationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO);

}
