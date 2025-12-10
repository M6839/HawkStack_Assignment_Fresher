package com.example.courseEnrollment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "progress", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lesson_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private Instant completedAt;
}
