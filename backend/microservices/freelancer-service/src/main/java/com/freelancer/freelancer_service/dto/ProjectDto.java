package com.freelancer.freelancer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Duration duration;
    private String status;
    private Integer assignedFreelancerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
