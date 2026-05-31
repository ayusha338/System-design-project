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
public class SectionRequest {

    private UUID teacherId;
    private Integer capacity;
    private String schedule;
    private String room;

}
