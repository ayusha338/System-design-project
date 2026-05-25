package com.coursera.course_registration.service;

import com.coursera.course_registration.dto.EnrollmentResponse;
import com.coursera.course_registration.model.Enrollment;
import com.coursera.course_registration.model.Section;
import com.coursera.course_registration.model.User;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    // Enroll karo — ENROLLED ya WAITLISTED return karega
    EnrollmentResponse enrollStudent(UUID studentId, UUID sectionId);

    // Drop karo
    void dropEnrollment(UUID studentId, UUID sectionId);

    // Student ke saare enrollments
    List<Enrollment> getEnrollmentsByStudentId(UUID studentId);

    // Section ka roster
    List<Enrollment> getEnrollmentsBySectionId(UUID sectionId);
}
