package com.client.client_service.feign;

import com.client.client_service.dto.Project;
import com.client.client_service.dto.ProjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "PROJECT-SERVICE")
public interface ProjectInterface {
    @PostMapping("/projects/create")
    public ProjectResponse createProject(Project project);
}
