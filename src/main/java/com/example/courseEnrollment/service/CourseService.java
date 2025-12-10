package com.example.courseEnrollment.service;


import com.example.courseEnrollment.entity.Course;
import com.example.courseEnrollment.exception.ResourceNotFoundException;
import com.example.courseEnrollment.repository.CourseRepository;
import com.example.courseEnrollment.repository.LessonRepository;

import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final com.example.courseEnrollment.repository.LessonRepository lessonRepository;

    public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    public long countLessons(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return lessonRepository.countByCourse(course);
    }
}
