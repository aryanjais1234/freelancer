package com.freelancer.freelancer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreelancerDto {
    private Integer freelancerId;
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String skills;
    private Double rating;
    private Integer completedProjects;
    private String availabilityStatus;
    private String profileSummary;
    private String portfolioLink;
    private String location;
    private String profilePictureUrl;
    private String phoneNumber;
    private String socialLinks;
    private String certifications;
    private String languages;
    private String hourlyRate;
    private String education;
    private String workExperience;
    private String additionalInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
