package com.LearnFree.LearnFreeServer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="grade_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GradeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="reg_num")
    private String registrationNumber;

    @Column(name="exam_name")
    private String examName;

    @Column(name="subject_name")
    private String subjectName;

    @Column(name="grade")
    private String grade;

    @Column(name="mark")
    private Double mark;

}
