package com.coursera.course_registration.Controller;

import com.coursera.course_registration.dto.CourseRequest;
import com.coursera.course_registration.dto.CourseResponse;
import com.coursera.course_registration.dto.SectionRequest;
import com.coursera.course_registration.model.Course;
import com.coursera.course_registration.model.Section;
import com.coursera.course_registration.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/{teacherId}")
    public ResponseEntity<CourseResponse> createCourse(@PathVariable UUID teacherId, @RequestBody CourseRequest courseRequest){
       Course course =  courseService.createCourse(teacherId,courseRequest);
       CourseResponse courseResponse = CourseResponse.builder()
               .id(course.getId())
               .title(course.getTitle())
               .description(course.getDescription())
               .teacherName(course.getTeacher().getName())
               .message("Course Enrolled!").build();
        return ResponseEntity
                .status(HttpStatus.CREATED)  // 201
                .body(courseResponse);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable UUID courseId){
        Course course = courseService.getCourseById(courseId);
        CourseResponse courseResponse = CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .teacherName(course.getTeacher().getName())
                .message("Course Enrolled!").build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseResponse);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourse(){
        List<Course> courses = courseService.getAllCourses();
        List<CourseResponse> response = courses.stream()
                .map(c -> CourseResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
                        .teacherName(c.getTeacher().getName())
                        .build())
                .toList();
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<CourseResponse>> getCourseByTeacherId(@PathVariable UUID teacherId){
        List<Course> courses = courseService.getCourseByTeacherId(teacherId);
        List<CourseResponse> response = courses.stream()
                .map(c -> CourseResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
                        .teacherName(c.getTeacher().getName())
                        .build())
                .toList();
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @PostMapping("/{courseId}/sections")
    public ResponseEntity<Section> addSection(@PathVariable UUID courseId, @RequestBody SectionRequest sectionRequest){
        Section section =  courseService.addSection(courseId,sectionRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // 201
                .body(section);
    }



}
