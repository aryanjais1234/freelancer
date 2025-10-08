package com.user.user_service.feign;

import com.user.user_service.dto.FreelancerUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "FREELANCER-SERVICE")
public interface FreelancerInterface {
    @PostMapping("/freelancers/create")
    public FreelancerUser createFreelancer(FreelancerUser freelancerUser);
}
