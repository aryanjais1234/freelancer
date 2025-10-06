package com.user.freelancer_user.feign;

import com.user.freelancer_user.dto.ClientUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "CLIENT-SERVICE")
public interface ClientInterface {
    @PostMapping("/clients/create")
    public ClientUser createClient(ClientUser clientUser);
}
