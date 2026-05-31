package com.coursera.course_registration.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @ControllerAdvice ResponseBody alag se lagana padta, that's why @REstControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyEnrolledException.class)
    public ResponseEntity<?> handle(AlreadyEnrolledException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<?> handle(SectionNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<?> handle(StudentNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(EnrollmentNotFoundException .class)
    public ResponseEntity<?> handle(EnrollmentNotFoundException  ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(TeacherNotFoundException .class)
    public ResponseEntity<?> handle(TeacherNotFoundException  ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(CourseNotFoundException .class)
    public ResponseEntity<?> handle(CourseNotFoundException  ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
