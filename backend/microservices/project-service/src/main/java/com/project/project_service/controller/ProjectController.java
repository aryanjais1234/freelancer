package com.project.project_service.controller;

import com.project.project_service.dto.ProjectDto;
import com.project.project_service.model.Project;
import com.project.project_service.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

}
