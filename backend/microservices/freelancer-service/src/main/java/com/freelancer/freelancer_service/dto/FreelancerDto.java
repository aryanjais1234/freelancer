package com.freelancer.freelancer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreelancerDto {
    private Integer freelancerId;
    private Integer userId; // Reference to User entity
    private String name;
    private String email;
    private String password;

}
