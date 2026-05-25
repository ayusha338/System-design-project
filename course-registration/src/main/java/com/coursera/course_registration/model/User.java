package com.coursera.course_registration.model;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name ="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /*
 1. Security ← Sabse important
// Long/Integer ID
GET /enrollments/101  → student guess kar sakta hai
GET /enrollments/102  → doosre ka data! 💥

// UUID
GET /enrollments/550e8400-e29b-41d4-a716-446655440000
→ guess karna practically impossible ✅

// Long ID — 2 servers pe same time record bana
Server 1 → id = 5
Server 2 → id = 5  💥 Conflict!

// UUID — har server independently generate kare
Server 1 → 550e8400-e29b-41d4...
Server 2 → 7f3d9200-a12c-31e4...  ✅ No conflict
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name ;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
}