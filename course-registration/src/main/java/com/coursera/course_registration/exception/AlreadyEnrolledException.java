package com.coursera.course_registration.exception;

public class AlreadyEnrolledException extends RuntimeException {

    public AlreadyEnrolledException(String message){
        super(message);
    }
}
