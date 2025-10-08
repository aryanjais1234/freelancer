package com.freelancer.freelancer_service.controller;

import com.freelancer.freelancer_service.dto.FreelancerDto;
import com.freelancer.freelancer_service.dto.ProjectDto;
import com.freelancer.freelancer_service.model.Freelancer;
import com.freelancer.freelancer_service.service.FreelancerService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/freelancers")
@RequiredArgsConstructor
public class FreelancerController {

    private final FreelancerService freelancerService;

    @PostMapping("/create")
    public ResponseEntity<FreelancerDto> createFreelancer(@RequestBody FreelancerDto freelancerDto) {
        return ResponseEntity.ok(freelancerService.createFreelancer(freelancerDto));
    }

    @GetMapping("/getProject/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(freelancerService.getProjectById(id));
    }
}
