package com.client.client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
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
}
