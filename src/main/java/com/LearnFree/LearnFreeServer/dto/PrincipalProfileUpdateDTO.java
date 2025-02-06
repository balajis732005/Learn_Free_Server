package com.LearnFree.LearnFreeServer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalProfileUpdateDTO {
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String mobileNumber;
    private String department;
    private String personalEmail;
    private String qualifications;
}