package com.coursera.course_registration.kafka.producer;

import com.coursera.course_registration.kafka.events.EnrollmentCreatedEvent;
import com.coursera.course_registration.kafka.events.SeatAvailableEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String ENROLLMENT_TOPIC = "enrollment-events";
    private static final String SEAT_AVAILABLE_TOPIC = "seat-available";

    public void publishEnrollmentCreated(EnrollmentCreatedEvent event) {
        kafkaTemplate.send(
                ENROLLMENT_TOPIC,
                event.getSectionId().toString(),
                event
        );
    }
    public void publishSeatAvailable(SeatAvailableEvent event) {
        kafkaTemplate.send(
                SEAT_AVAILABLE_TOPIC,
                event.getSectionId().toString(),
                event
        );
    }

}
