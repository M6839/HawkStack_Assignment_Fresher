package com.example.courseEnrollment.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courseEnrollment.entity.Course;
import com.example.courseEnrollment.entity.Lesson;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse(Course course);
    long countByCourse(Course course);
}
