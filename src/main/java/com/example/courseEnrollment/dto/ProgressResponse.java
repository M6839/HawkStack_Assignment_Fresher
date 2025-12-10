package com.example.courseEnrollment.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgressResponse {
    public ProgressResponse(Long courseId2, long completed, long total, double percent) {
		// TODO Auto-generated constructor stub
	}	
	private Long courseId;
    private long completedLessons;
    private long totalLessons;
    private double progressPercentage;
}
