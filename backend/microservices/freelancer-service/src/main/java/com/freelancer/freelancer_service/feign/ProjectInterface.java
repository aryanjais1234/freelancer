package com.freelancer.freelancer_service.feign;

import com.freelancer.freelancer_service.dto.ProjectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PROJECT-SERVICE")
public interface ProjectInterface {
    @GetMapping("/projects/getProject/{id}")
    ProjectDto getProjectById(@PathVariable Integer id);

    @GetMapping("/projects/open")
    List<ProjectDto> getOpenProjects();
}
