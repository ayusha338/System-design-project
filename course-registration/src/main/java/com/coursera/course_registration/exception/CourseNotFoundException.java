package com.coursera.course_registration.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String message){
        super(message);
    }
}
