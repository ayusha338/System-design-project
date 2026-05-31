package com.coursera.course_registration.service;

import com.coursera.course_registration.dto.CourseRequest;
import com.coursera.course_registration.dto.SectionRequest;
import com.coursera.course_registration.model.Course;
import com.coursera.course_registration.model.Section;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course createCourse(UUID teacherId, CourseRequest request);

    Course getCourseById(UUID courseId);

    List<Course> getAllCourses();

    List<Course> getCourseByTeacherId(UUID teacherId);

    Section addSection(UUID courseId, SectionRequest request);
}
