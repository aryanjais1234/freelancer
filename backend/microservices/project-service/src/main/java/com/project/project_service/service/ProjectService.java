package com.project.project_service.service;

import com.project.project_service.dao.ProjectRepository;
import com.project.project_service.dto.ProjectDto;
import com.project.project_service.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectDto createProject(Project project) {
        if (project.getStatus() == null) {
            project.setStatus("OPEN");
        }
        Project savedProject = projectRepository.save(project);
        return mapToDto(savedProject);
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getOpenProjects() {
        return projectRepository.findByStatus("OPEN").stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getProjectsByClient(Integer clientId) {
        return projectRepository.findByClientId(clientId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getProjectsByCategory(String category) {
        return projectRepository.findByCategoryIgnoreCase(category).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void updateProjectStatus(Integer projectId, String status, Integer assignedFreelancerId) {
        Optional<Project> optProject = projectRepository.findById(projectId);
        if (optProject.isPresent()) {
            Project project = optProject.get();
            project.setStatus(status);
            if (assignedFreelancerId != null) {
                project.setAssignedFreelancerId(assignedFreelancerId);
            }
            projectRepository.save(project);
        } else {
            throw new RuntimeException("Project not found with id: " + projectId);
        }
    }

    public ProjectDto getProjectById(Integer id) {
        Optional<Project> project = projectRepository.findById(id);
        ProjectDto projectDto = mapToDto(project.orElse(null));
        System.out.println(projectDto);
        return projectDto;
    }

    private ProjectDto mapToDto(Project project) {
        ProjectDto dto = new ProjectDto();
        if (project == null) return dto;
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setClientId(project.getClientId());
        dto.setBudget(project.getBudget());
        dto.setBudgetMin(project.getBudgetMin());
        dto.setBudgetMax(project.getBudgetMax());
        dto.setCategory(project.getCategory());
        dto.setDeadline(project.getDeadline());
        dto.setDuration(project.getDuration());
        dto.setStatus(project.getStatus());
        dto.setAssignedFreelancerId(project.getAssignedFreelancerId());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        return dto;
    }
}
