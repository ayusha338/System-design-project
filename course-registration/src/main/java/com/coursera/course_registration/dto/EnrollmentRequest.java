package com.coursera.course_registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {
    private UUID studentId;
    private UUID sectionId;
}