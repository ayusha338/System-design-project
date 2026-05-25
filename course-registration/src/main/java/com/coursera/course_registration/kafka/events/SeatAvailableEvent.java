package com.coursera.course_registration.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatAvailableEvent {
    private UUID sectionId;
    private int availableSeats;
    private LocalDateTime releasedAt;
}
