package com.coursera.course_registration.Controller;

import com.coursera.course_registration.dto.EnrollmentRequest;
import com.coursera.course_registration.dto.EnrollmentResponse;
import com.coursera.course_registration.model.Enrollment;
import com.coursera.course_registration.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enrollStudent(
            @RequestBody EnrollmentRequest enrollmentRequest){
        EnrollmentResponse enrollmentResponse = enrollmentService.enrollStudent(
                enrollmentRequest.getStudentId(),enrollmentRequest.getSectionId());
        return ResponseEntity
                .status(HttpStatus.CREATED)  // 201
                .body(enrollmentResponse);

    }

    @DeleteMapping("/{studentId}/{sectionId}")
    public ResponseEntity<Void> dropEnrollment(
            @PathVariable UUID studentId,
            @PathVariable UUID sectionId){
        enrollmentService.dropEnrollment(studentId,sectionId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<Enrollment>> getStudentEnrollments(
            @PathVariable UUID studentId){
        List<Enrollment> enrollList = enrollmentService.getEnrollmentsByStudentId(studentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(enrollList);
    }

    @GetMapping("/sections/{sectionId}")
    public ResponseEntity<List<Enrollment>> getSectionEnrollments(
            @PathVariable UUID sectionId) {
        List<Enrollment> enrollList = enrollmentService.getEnrollmentsBySectionId(sectionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(enrollList);
    }

}
