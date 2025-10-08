package com.user.user_service.feign;

import com.user.user_service.dto.ClientUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "CLIENT-SERVICE")
public interface ClientInterface {
    @PostMapping("/clients/create")
    public ClientUser createClient(ClientUser clientUser);
}
