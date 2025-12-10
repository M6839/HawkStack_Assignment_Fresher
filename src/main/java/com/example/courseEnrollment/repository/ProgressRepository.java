package com.example.courseEnrollment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.courseEnrollment.entity.Course;
import com.example.courseEnrollment.entity.Lesson;
import com.example.courseEnrollment.entity.Progress;
import com.example.courseEnrollment.entity.User;

import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    boolean existsByUserAndLesson(User user, Lesson lesson);
    Optional<Progress> findByUserAndLesson(User user, Lesson lesson);

    @Query("SELECT count(p) FROM Progress p WHERE p.user = :user AND p.lesson.course = :course")
    long countByUserAndCourse(User user, Course course);
}
