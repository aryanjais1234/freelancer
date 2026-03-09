package com.client.client_service.feign;

import com.client.client_service.dto.ProjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PROJECT-SERVICE")
public interface ProjectQueryInterface {
    @GetMapping("/projects/client/{clientId}")
    List<ProjectResponse> getProjectsByClient(@PathVariable Integer clientId);
}
