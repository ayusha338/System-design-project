package com.coursera.course_registration.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionRequest {

    private UUID teacherId;
    private Integer capacity;
    private String schedule;
    private String room;

}
