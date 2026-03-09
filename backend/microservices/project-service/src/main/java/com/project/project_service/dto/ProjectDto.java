package com.project.project_service.dto;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProjectDto {
    private Integer id;
    private Integer clientId;
    private String title;
    private String description;
    private Double budget;
    private Double budgetMin;
    private Double budgetMax;
    private String category;
    private LocalDate deadline;
    private Duration duration; // e.g., in days
    private String status; // e.g., "OPEN", "IN_PROGRESS", "COMPLETED"
    private Integer assignedFreelancerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
