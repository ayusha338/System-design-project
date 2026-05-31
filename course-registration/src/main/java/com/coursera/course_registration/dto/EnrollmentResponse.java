package com.coursera.course_registration.dto;

import lombok.*;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponse {
    private String status;      // "ENROLLED" ya "WAITLISTED"
    private UUID id;            // enrollmentId ya waitlistId
    private Integer position;   // sirf WAITLISTED ke liye
    private String message;
}
