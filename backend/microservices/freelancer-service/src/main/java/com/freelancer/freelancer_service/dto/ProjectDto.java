package com.freelancer.freelancer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Integer id;
    private Integer clientId;
    private String title;
    private String description;
    private Double budget;
    private Duration duration; // e.g., in days
    private String status; // e.g., "OPEN", "IN_PROGRESS", "COMPLETED"
}
