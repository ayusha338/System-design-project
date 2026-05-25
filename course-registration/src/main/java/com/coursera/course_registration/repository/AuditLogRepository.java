package com.coursera.course_registration.repository;

import com.coursera.course_registration.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog , UUID> {

    List<AuditLog> findByEntityIdOrderByChangedAtDesc(UUID entityId);


}
