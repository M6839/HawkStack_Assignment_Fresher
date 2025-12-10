package com.example.courseEnrollment.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courseEnrollment.entity.Course;
import com.example.courseEnrollment.entity.Enrollment;
import com.example.courseEnrollment.entity.User;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserAndCourse(User user, Course course);
    Optional<Enrollment> findByUserAndCourse(User user, Course course);
}
