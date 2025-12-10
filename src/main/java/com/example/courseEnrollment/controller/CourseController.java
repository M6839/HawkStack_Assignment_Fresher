package com.example.courseEnrollment.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.courseEnrollment.dto.ProgressResponse;
import com.example.courseEnrollment.exception.ConflictException;
import com.example.courseEnrollment.service.*;

@RestController
@RequestMapping("/")
public class CourseController {
    private final EnrollmentService enrollmentService;
    private final ProgressService progressService;
    private final CourseService courseService;

    public CourseController(EnrollmentService enrollmentService,
                            ProgressService progressService,
                            CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.progressService = progressService;
        this.courseService = courseService;
    }

    // Enroll
    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<?> enroll(@PathVariable Long courseId,
                                    @RequestParam Long userId) {
        try {
            enrollmentService.enroll(userId, courseId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Enrollment successful");
        } catch (ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Complete lesson (idempotent)
    @PostMapping("/courses/{courseId}/lessons/{lessonId}/complete")
    public ResponseEntity<?> completeLesson(@PathVariable Long courseId,
                                            @PathVariable Long lessonId,
                                            @RequestParam Long userId) {

        // check enrollment
        if (!enrollmentService.isEnrolled(userId, courseId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not enrolled in this course");
        }

        boolean saved = progressService.completeLesson(userId, courseId, lessonId);
        if (!saved) {
            // idempotent: already completed
            return ResponseEntity.ok("Lesson already completed (idempotent)");
        }
        return ResponseEntity.ok("Lesson completed");
    }

    // Get progress
    @GetMapping("/users/{userId}/courses/{courseId}/progress")
    public ResponseEntity<?> getProgress(@PathVariable Long userId,
                                         @PathVariable Long courseId) {
        if (!enrollmentService.isEnrolled(userId, courseId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not enrolled in this course");
        }

        long completed = progressService.countCompletedLessons(userId, courseId);
        long total = courseService.countLessons(courseId);
        double percent = total == 0 ? 0.0 : (completed * 100.0 / total);

        ProgressResponse resp = new ProgressResponse(courseId, completed, total, percent);
        return ResponseEntity.ok(resp);
    }
}
