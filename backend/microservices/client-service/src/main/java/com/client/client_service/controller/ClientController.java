package com.client.client_service.controller;

import com.client.client_service.dto.ClientDto;
import com.client.client_service.model.Client;
import com.client.client_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create")
    public ClientDto createClient(@RequestBody ClientDto client) {
        try {
            return clientService.createClient(client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getClientDetails/{id}")
    public Client getClientDetails(@PathVariable Integer id) {
        return clientService.getClientDetails(id);
    }
}
