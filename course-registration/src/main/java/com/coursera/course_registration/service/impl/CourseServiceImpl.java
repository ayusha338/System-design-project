package com.coursera.course_registration.service.impl;

import com.coursera.course_registration.dto.CourseRequest;
import com.coursera.course_registration.dto.SectionRequest;
import com.coursera.course_registration.exception.CourseNotFoundException;
import com.coursera.course_registration.exception.TeacherNotFoundException;
import com.coursera.course_registration.model.Course;
import com.coursera.course_registration.model.Section;
import com.coursera.course_registration.model.User;
import com.coursera.course_registration.repository.CourseRepository;
import com.coursera.course_registration.repository.SectionRepository;
import com.coursera.course_registration.repository.UserRepository;
import com.coursera.course_registration.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final SectionRepository sectionRepository;

    @Override
    public Course createCourse(UUID teacherId, CourseRequest request) {
        User user = userRepository
                .findById(teacherId)
                .orElseThrow(()-> new TeacherNotFoundException("Teacher not found!"));
        Course course = Course.builder()
                .title(request.getTitle())
                .teacher(user)
                .description(request.getDescription()).build();
        courseRepository.save(course);
        return course;
    }

    @Override
    public Course getCourseById(UUID courseId) {
        return courseRepository
                .findById(courseId)
                .orElseThrow(()-> new CourseNotFoundException("Course Not Found!"));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getCourseByTeacherId(UUID teacherId) {
        return courseRepository.findByTeacher_Id(teacherId);
    }

    @Override
    public Section addSection(UUID courseId, SectionRequest request) {
        Course course  = courseRepository
                .findById(courseId)
                .orElseThrow(()-> new CourseNotFoundException("Course Not Found!"));
        User u = userRepository
                .findById(request.getTeacherId())
                .orElseThrow(()-> new TeacherNotFoundException("Teacher Not Found!"));
        Section section = Section.builder()
                .course(course)
                .teacher(u)
                .capacity(request.getCapacity())
                .schedule(request.getSchedule())
                .room(request.getRoom())
                .build();
        sectionRepository.save(section);
        return section;
    }
}
