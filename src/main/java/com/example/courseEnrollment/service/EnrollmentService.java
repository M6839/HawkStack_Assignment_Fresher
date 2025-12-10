package com.example.courseEnrollment.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.courseEnrollment.entity.Course;
import com.example.courseEnrollment.entity.Enrollment;
import com.example.courseEnrollment.entity.User;
import com.example.courseEnrollment.exception.ConflictException;
import com.example.courseEnrollment.exception.ResourceNotFoundException;
import com.example.courseEnrollment.repository.CourseRepository;
import com.example.courseEnrollment.repository.EnrollmentRepository;
import com.example.courseEnrollment.repository.UserRepository;

import java.time.Instant;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             UserRepository userRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void enroll(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        if (enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new ConflictException("User already enrolled in course");
        }

        Enrollment e = Enrollment.builder()
                .user(user)
                .course(course)
                .enrolledAt(Instant.now())
                .build();
        enrollmentRepository.save(e);
    }

    public boolean isEnrolled(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        if (user == null || course == null) return false;
        return enrollmentRepository.existsByUserAndCourse(user, course);
    }
}
