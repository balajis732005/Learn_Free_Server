package com.LearnFree.LearnFreeServer.controller;

import com.LearnFree.LearnFreeServer.entity.Certificate;
import com.LearnFree.LearnFreeServer.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/learn-free/certificates")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateRepository certificateRepository;

    @GetMapping
    public ResponseEntity<List<Certificate>> getCertificates(@RequestParam Long userId) {
        return ResponseEntity.ok(certificateRepository.findByUserId(userId));
    }
}
