package com.client.client_service.controller;

import com.client.client_service.dto.ClientDto;
import com.client.client_service.dto.Project;
import com.client.client_service.dto.ProjectResponse;
import com.client.client_service.model.Client;
import com.client.client_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Client> getClientProfile(@PathVariable Integer userId) {
        Client client = clientService.getClientByUserId(userId);
        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<ClientDto> updateClientProfile(@PathVariable Integer userId,
                                                         @RequestBody ClientDto clientDto) {
        ClientDto updated = clientService.updateClientProfile(userId, clientDto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createProject")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody Project project,
                                                         @RequestHeader("username") String username,
                                                         @RequestHeader("userId") String userId) {
        System.out.println("Request received to create project by user: " + username + " with userId: " + userId);
        Integer clientId = Integer.parseInt(userId);
        if (clientId != null) {
            project.setClientId(clientId);
            return ResponseEntity.ok(clientService.createProject(project, userId));
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/projects/{userId}")
    public ResponseEntity<List<ProjectResponse>> getClientProjects(@PathVariable Integer userId) {
        List<ProjectResponse> projects = clientService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }
}
