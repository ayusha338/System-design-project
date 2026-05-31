package com.coursera.course_registration.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionResponse {
    private UUID id;
    private Integer capacity;
    private Integer enrolledCount;
    private String schedule;
    private String room;
}
