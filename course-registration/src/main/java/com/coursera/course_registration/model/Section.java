package com.coursera.course_registration.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "sections")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    // There can be multiple sections for One Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="teacher_id", nullable = false)
    private User teacher;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    @Builder.Default
    private Integer enrolledCount = 0 ;

    @Column(nullable = false)
    private String schedule;

    @Column(nullable = false)
    private String room ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SectionStatus status = SectionStatus.OPEN;

    public boolean hasAvailableSeat(){
        return enrolledCount < capacity;
    }

    public void incrementEnrolledCount() {
        if (!hasAvailableSeat()) {
            throw new IllegalStateException("Section is full!");
        }
        this.enrolledCount++;
        if (this.enrolledCount == this.capacity) {
            this.status = SectionStatus.FULL;  // automatically FULL mark
        }
    }

    public void decrementEnrolledCount() {
        if (this.enrolledCount <= 0) {
            throw new IllegalStateException("Count cannot go below 0!");
        }
        this.enrolledCount--;
        // Seat free hui — reopen karo agar FULL tha
        if (this.status == SectionStatus.FULL) {
            this.status = SectionStatus.OPEN;
        }
    }
}
