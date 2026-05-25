package com.coursera.course_registration.service.impl;

import com.coursera.course_registration.dto.EnrollmentResponse;
import com.coursera.course_registration.model.*;
import com.coursera.course_registration.repository.EnrollmentRepository;
import com.coursera.course_registration.repository.SectionRepository;
import com.coursera.course_registration.repository.UserRepository;
import com.coursera.course_registration.repository.WaitListRepository;
import com.coursera.course_registration.service.EnrollmentService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final WaitListRepository waitListRepository;

    @Transactional
    @Override
    public EnrollmentResponse enrollStudent(UUID studentId, UUID sectionId) {
    if(enrollmentRepository.existsByStudent_IdAndSection_Id(studentId, sectionId)){
        throw new RuntimeException("Student already enrolled!");
    }
    User student = userRepository.findById(studentId)
            .orElseThrow(()-> new RuntimeException("Student not found!"));

    Section section = sectionRepository.findByIdWithLock(sectionId)
            .orElseThrow(()-> new RuntimeException("Section not found!"));
    if(section.hasAvailableSeat()) {
        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .section(section)
                .status(EnrollmentStatus.ENROLLED)
                .build();
        section.incrementEnrolledCount();
        enrollmentRepository.save(enrollment);
        sectionRepository.save(section);

        return EnrollmentResponse.builder()
                .status("ENROLLED")
                .id(enrollment.getId())
                .message("Successfully enrolled!")
                .build();
    }else{
        int nextPosition = waitListRepository.findMaxPosition(sectionId) + 1;
        WaitList waitList =  WaitList.builder()
                .student(student)
                .section(section)
                .position(nextPosition)
                .status(WaitlistStatus.WAITING)
                .build();
        waitListRepository.save(waitList);
        return EnrollmentResponse.builder()
                .status("WAITLISTED")
                .id(waitList.getId())
                .position(nextPosition)
                .message("Section full! Added to waitlist at position " + nextPosition)
                .build();
    }
    }

    @Transactional
    @Override
    public void dropEnrollment(UUID studentId, UUID sectionId) {
        Enrollment enrollment = enrollmentRepository.findByStudent_IdAndSection_Id(studentId, sectionId)
                .orElseThrow(()-> new RuntimeException("Enrollment not found!"));
            Section section = sectionRepository.findByIdWithLock(sectionId)
                    .orElseThrow(()-> new RuntimeException("Section not found!"));

            enrollment.setStatus(EnrollmentStatus.DROPPED);
            enrollment.setDroppedAt(LocalDateTime.now());
        enrollmentRepository.save(enrollment);
        section.decrementEnrolledCount();
        sectionRepository.save(section);
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudentId(UUID studentId) {
        return enrollmentRepository.findActiveByStudentId(studentId);
    }

    @Override
    public List<Enrollment> getEnrollmentsBySectionId(UUID sectionId) {
        return enrollmentRepository.findActiveBySectionId(sectionId);
    }
}
