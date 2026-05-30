package com.coursera.course_registration.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "courses")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     Aggregation vs Composition:
     Composition — strong bond ("part of")
     Course → Sections
     "Agar Course delete ho → Sections bhi delete ho jaayein"
     Section ka Course ke bina koi existence nahi ✅
     → CascadeType.ALL lagaya humne
     Aggregation — loose bond ("has a")
     Course → Teacher
     "Agar Course delete ho → Teacher delete nahi hoga"
     Teacher ka Course ke bina bhi existence hai ✅
     → Sirf @ManyToOne lagaya, koi cascade nahi
     MAny to One :
     Ek Teacher → Multiple Courses padha sakta hai
     Ek Course  → Sirf ek Teacher hota hai
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    // A course can have multiple sections
    //
// ✅ Sahi — Section.java mein field ka naam "course" hoga
/*
    CascadeType.ALL isliye ki section ka Course ke bina existence nahi —
    composition relationship hai.
    LAZY fetch isliye ki N+1 problem avoid ho.
    @Builder.Default isliye ki empty list mile null nahi."
* */
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Section> sections = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}

