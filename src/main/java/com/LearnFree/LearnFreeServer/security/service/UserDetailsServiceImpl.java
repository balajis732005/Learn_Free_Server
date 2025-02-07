package com.LearnFree.LearnFreeServer.security.service;

import com.LearnFree.LearnFreeServer.repository.UserAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        return userAuthenticationRepository.findByEmail(userEmail);
    }
}
