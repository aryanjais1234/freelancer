package com.client.client_service.controller;

import com.client.client_service.dto.ClientDto;
import com.client.client_service.dto.Project;
import com.client.client_service.dto.ProjectResponse;
import com.client.client_service.model.Client;
import com.client.client_service.service.ClientService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/createProject")
            public ResponseEntity<ProjectResponse> createProject(@RequestBody Project project,
                                                                 @RequestHeader("username") String username,
                                                                 @RequestHeader("userId") String userId) {
                System.out.println("Request received to create project by user: " + username + " with userId: " + userId);
//                Integer clientId = clientService.getClientId(username);
                Integer clientId = Integer.parseInt(userId);
                if (clientId != null) {
                    project.setClientId(clientId);
                    return ResponseEntity.ok(clientService.createProject(project,userId));
                } else {
                    return ResponseEntity.status(404).body(null);
                }
            }
}
