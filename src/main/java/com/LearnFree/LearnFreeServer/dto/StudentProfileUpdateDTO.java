package com.LearnFree.LearnFreeServer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfileUpdateDTO {
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String mobileNumber;
    private String department;
    private String personalEmail;
    private String registrationNumber;
    private String github;
    private String linkedIn;
}