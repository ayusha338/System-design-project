package com.coursera.course_registration.repository;

import com.coursera.course_registration.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    List<Enrollment> findBySection_Id(UUID sectionId);

    // 1. Student already enrolled hai? — duplicate check
    boolean existsByStudent_IdAndSection_Id(UUID studentId, UUID sectionId);

    // 2. Student ke saare active enrollments
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.status = 'ENROLLED'")
    List<Enrollment> findActiveByStudentId(@Param("studentId") UUID studentId);

    // 3. Section ka roster
    @Query("SELECT e FROM Enrollment e WHERE e.section.id = :sectionId AND e.status = 'ENROLLED'")
    List<Enrollment> findActiveBySectionId(@Param("sectionId") UUID sectionId);

    // 4. Specific enrollment dhundo — drop karne ke liye
    Optional<Enrollment> findByStudent_IdAndSection_Id(UUID studentId, UUID sectionId);
}
