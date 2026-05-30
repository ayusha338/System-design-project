package com.coursera.course_registration.exception;


public class EnrollmentNotFoundException extends RuntimeException {

    public EnrollmentNotFoundException(String message){
        super(message);
    }
}
