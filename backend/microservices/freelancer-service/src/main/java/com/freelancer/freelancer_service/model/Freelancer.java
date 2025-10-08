package com.freelancer.freelancer_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity(name = "freelancers_table")
@AllArgsConstructor
@NoArgsConstructor
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer freelancerId;
    private Integer userId; // Reference to User entity
    private String name;
    private String email;
    private String password;
    private String skills; // Comma-separated skills
    private Double rating; // Average rating from clients
    private Integer completedProjects; // Number of completed projects
    private String availabilityStatus; // e.g., "AVAILABLE", "BUSY"
    private String profileSummary; // Short bio or summary
    private String portfolioLink; // Link to portfolio or personal website
    private String location; // Optional: Location of the freelancer

    private String profilePictureUrl; // Optional: URL to profile picture
    private String phoneNumber; // Optional: Contact phone number
    private String socialLinks; // Optional: Comma-separated social media links
    private String certifications; // Optional: Comma-separated certifications
    private String languages; // Optional: Comma-separated languages spoken
    private String hourlyRate; // Optional: Hourly rate for services
    private String education; // Optional: Educational background
    private String workExperience; // Optional: Summary of work experience
    private String additionalInfo; // Optional: Any additional information

    @CreationTimestamp
    private LocalDateTime createdAt; // Timestamp of creation
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Timestamp of last update
}
