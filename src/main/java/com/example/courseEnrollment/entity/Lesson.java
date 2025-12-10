package com.example.courseEnrollment.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lessons", uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "position"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer position; // order within course

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
