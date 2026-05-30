package com.coursera.course_registration.exception;

public class SectionNotFoundException extends RuntimeException{
    public SectionNotFoundException(String message){
        super(message);
    }
}
