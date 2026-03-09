package com.bid.bid_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PROJECT-SERVICE")
public interface ProjectInterface {

    @PutMapping("/projects/{id}/status")
    void updateProjectStatus(@PathVariable Integer id,
                             @RequestParam String status,
                             @RequestParam(required = false) Integer assignedFreelancerId);
}
