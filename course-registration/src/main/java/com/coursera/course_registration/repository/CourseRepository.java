package com.coursera.course_registration.repository;

import com.coursera.course_registration.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findByTeacher_Id(UUID teacherId);

    List<Course> findByTitleContainingIgnoreCase(String title);
}
