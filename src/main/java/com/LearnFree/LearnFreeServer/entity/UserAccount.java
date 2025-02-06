package com.LearnFree.LearnFreeServer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="gender")
    private String gender;

    @Column(name="date_of_birth")
    private String dateOfBirth;

    @Column(name="mobile_number")
    private String mobileNumber;

    @Column(name="department")
    private String department;

    @Column(name="personal_email")
    private String personalEmail;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "github")
    private String github;

    @Column(name = "linked_in")
    private String linkedIn;

    @Column(name = "qualifications")
    private String qualifications;
}