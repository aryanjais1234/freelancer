package com.freelancer.freelancer_service.controller;

import com.freelancer.freelancer_service.dto.FreelancerDto;
import com.freelancer.freelancer_service.dto.ProjectDto;
import com.freelancer.freelancer_service.service.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/freelancers")
@RequiredArgsConstructor
public class FreelancerController {

    private final FreelancerService freelancerService;

    @PostMapping("/create")
    public ResponseEntity<FreelancerDto> createFreelancer(@RequestBody FreelancerDto freelancerDto) {
        return ResponseEntity.ok(freelancerService.createFreelancer(freelancerDto));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<FreelancerDto> getFreelancerProfile(@PathVariable Integer userId) {
        FreelancerDto dto = freelancerService.getFreelancerByUserId(userId);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<FreelancerDto> updateFreelancerProfile(@PathVariable Integer userId,
                                                                  @RequestBody FreelancerDto freelancerDto) {
        FreelancerDto updated = freelancerService.updateFreelancerProfile(userId, freelancerDto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FreelancerDto>> searchFreelancers(@RequestParam String skill) {
        return ResponseEntity.ok(freelancerService.searchBySkill(skill));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FreelancerDto>> getAllFreelancers() {
        return ResponseEntity.ok(freelancerService.getAllFreelancers());
    }

    @GetMapping("/getProject/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(freelancerService.getProjectById(id));
    }

    @GetMapping("/projects/open")
    public ResponseEntity<List<ProjectDto>> getOpenProjects() {
        return ResponseEntity.ok(freelancerService.getOpenProjects());
    }
}
