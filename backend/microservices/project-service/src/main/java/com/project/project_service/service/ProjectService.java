package com.project.project_service.service;

import com.project.project_service.dao.ProjectRepository;
import com.project.project_service.dto.ProjectDto;
import com.project.project_service.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectDto createProject(Project project) {
        Project savedProject = projectRepository.save(project);
        ProjectDto projectDto = new ProjectDto();
        mapToProject(projectDto, savedProject);
        return projectDto;
    }

    private void mapToProject(ProjectDto projectDto, Project project) {
        projectDto.setId(project.getId());
        projectDto.setTitle(project.getTitle());
        projectDto.setDescription(project.getDescription());
        projectDto.setClientId(project.getClientId());
        projectDto.setBudget(project.getBudget());
        projectDto.setDuration(project.getDuration());
        projectDto.setStatus(project.getStatus());
    }

}
