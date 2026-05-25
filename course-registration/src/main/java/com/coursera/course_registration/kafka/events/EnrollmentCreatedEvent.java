package com.coursera.course_registration.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentCreatedEvent {
    private UUID enrollmentId;
    private UUID studentId;
    private String studentEmail;
    private UUID sectionId;
    private LocalDateTime enrolledAt;
}
