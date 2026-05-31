package com.coursera.course_registration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionResponse {
    private UUID id;
    private String teacherName;
    private Integer capacity;
    private Integer enrolledCount;
    private String schedule;
    private String room;
    private String sectionStatus;
}
