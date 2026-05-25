package com.coursera.course_registration.repository;

import com.coursera.course_registration.model.Section;
import com.coursera.course_registration.model.WaitList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WaitListRepository extends JpaRepository<WaitList, UUID> {

    List<WaitList> findBySection_Id(UUID id);

    Optional<WaitList> findByStudent_IdAndSection_Id(UUID studentId, UUID sectionId);

    boolean existsByStudent_IdAndSection_Id(UUID studentId, UUID sectionId);

    @Query("""
            SELECT w FROM WaitList w 
            where w.section.id = :sectionId 
            AND w.status = 'WAITING'
            ORDER BY w.position ASC 
            LIMIT 1
""")
    Optional<WaitList> findTopWaiting(@Param("sectionId") UUID sectionId);

    @Query("""
    SELECT COALESCE(MAX(w.position), 0)
    FROM WaitList w
    WHERE w.section.id = :sectionId
    AND w.status = 'WAITING'
    """)
    int findMaxPosition(@Param("sectionId") UUID sectionId);
}
