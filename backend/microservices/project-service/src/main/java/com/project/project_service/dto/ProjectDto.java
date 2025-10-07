package com.project.project_service.dto;

import lombok.Data;

import java.time.Duration;
@Data
public class ProjectDto {
    private Integer id;
    private Integer clientId;
    private String title;
    private String description;
    private Double budget;
    private Duration duration; // e.g., in days
    private String status; // e.g., "OPEN", "IN_PROGRESS", "COMPLETED"
}
