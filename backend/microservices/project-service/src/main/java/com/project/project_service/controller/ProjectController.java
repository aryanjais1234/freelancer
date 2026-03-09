package com.project.project_service.controller;

import com.project.project_service.dto.ProjectDto;
import com.project.project_service.model.Project;
import com.project.project_service.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    @GetMapping("/getProject/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/open")
    public ResponseEntity<List<ProjectDto>> getOpenProjects() {
        return ResponseEntity.ok(projectService.getOpenProjects());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ProjectDto>> getProjectsByClient(@PathVariable Integer clientId) {
        return ResponseEntity.ok(projectService.getProjectsByClient(clientId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProjectDto>> getProjectsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(projectService.getProjectsByCategory(category));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateProjectStatus(@PathVariable Integer id,
                                                    @RequestParam String status,
                                                    @RequestParam(required = false) Integer assignedFreelancerId) {
        projectService.updateProjectStatus(id, status, assignedFreelancerId);
        return ResponseEntity.ok().build();
    }

}
