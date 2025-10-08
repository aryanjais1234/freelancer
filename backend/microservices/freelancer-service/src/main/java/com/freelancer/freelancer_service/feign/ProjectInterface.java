package com.freelancer.freelancer_service.feign;

import com.freelancer.freelancer_service.dto.ProjectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PROJECT-SERVICE")
public interface ProjectInterface {
    @GetMapping("/projects/getProject/{id}")
    public ProjectDto getProjectById(@PathVariable Integer id);
}
