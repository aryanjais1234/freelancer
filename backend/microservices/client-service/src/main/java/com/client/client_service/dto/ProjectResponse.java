package com.client.client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Integer id;
    private Integer clientId;
    private String title;
    private String description;
    private Double budget;
    private Duration duration; // e.g., in days
    private String status; // e.g., "OPEN", "IN_PROGRESS", "COMPLETED"
}
