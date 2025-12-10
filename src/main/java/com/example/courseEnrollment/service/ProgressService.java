package com.example.courseEnrollment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.courseEnrollment.entity.Course;
import com.example.courseEnrollment.entity.Lesson;
import com.example.courseEnrollment.entity.Progress;
import com.example.courseEnrollment.entity.User;
import com.example.courseEnrollment.exception.ResourceNotFoundException;
import com.example.courseEnrollment.repository.CourseRepository;
import com.example.courseEnrollment.repository.LessonRepository;
import com.example.courseEnrollment.repository.ProgressRepository;
import com.example.courseEnrollment.repository.UserRepository;

import java.time.Instant;

@Service
public class ProgressService {
    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public ProgressService(ProgressRepository progressRepository,
                           UserRepository userRepository,
                           LessonRepository lessonRepository,
                           CourseRepository courseRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public boolean completeLesson(Long userId, Long courseId, Long lessonId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        if (!lesson.getCourse().getId().equals(course.getId())) {
            throw new ResourceNotFoundException("Lesson does not belong to course");
        }

        // Idempotency: if exists, do nothing and return false meaning "already completed"
        if (progressRepository.existsByUserAndLesson(user, lesson)) {
            return false;
        }

        Progress p = Progress.builder()
                .user(user)
                .lesson(lesson)
                .completedAt(Instant.now())
                .build();
        progressRepository.save(p);
        return true;
    }

    public long countCompletedLessons(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return progressRepository.countByUserAndCourse(user, course);
    }
}
