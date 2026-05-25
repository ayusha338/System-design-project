package com.coursera.course_registration.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "waitlist",
       //Ek student ek section ki waitlist pe sirf ek baar hona chahiye!
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_waitlist_student_section",
                        columnNames = {"student_id", "section_id"}
                )
        }
)
public class WaitList {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
    Ek Student → Multiple waitlists pe ho sakta hai
    Ek Waitlist entry → Sirf ek Student ka hota hai
    → ManyToOne on student
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", nullable = false)
    private User student;
    /*
    Ek Section → Multiple waitlist entries ho sakti hain
    Ek Waitlist entry → Sirf ek Section ka hota hai
    → ManyToOne on section
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Column(nullable = false)
    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private WaitlistStatus status = WaitlistStatus.WAITING;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime joinedAt;
}

