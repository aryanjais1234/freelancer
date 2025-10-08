package com.client.client_service.service;

import com.client.client_service.dao.ClientRepository;
import com.client.client_service.dto.ClientDto;
import com.client.client_service.dto.Project;
import com.client.client_service.dto.ProjectResponse;
import com.client.client_service.feign.ProjectInterface;
import com.client.client_service.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final ProjectInterface projectInterface;



    public Client getClientDetails(Integer id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            return client.get();
        } else {
            System.out.println("Client not found with id: " + id);
            return null;
        }
    }

    public ClientDto createClient(ClientDto clientDto) {
        try {
            Client newClient = new Client();
            mapToClient(clientDto, newClient);
            clientRepository.save(newClient);
            return clientDto;
        } catch (Exception e) {
            return null;
        }
    }

    private void mapToClient(ClientDto clientDto, Client client) {
        client.setUserId(clientDto.getUserId());
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword());
    }

    public ProjectResponse createProject(Project project, String userId) {
        ProjectResponse projectResponse = new ProjectResponse();
        Optional<Client> client = clientRepository.findByUserId(Integer.parseInt(userId));
        if(client.isPresent()){
            projectResponse = projectInterface.createProject(project);
            Client projectClient = client.get();
            if (projectClient.getProjectIds() == null) {
                projectClient.setProjectIds(new ArrayList<>());
            }
            projectClient.getProjectIds().add(projectResponse.getId());
            clientRepository.save(projectClient);
        } else {

        }
        return projectResponse;
    }

    public Integer getClientId(String username) {
        Optional<Client> client = clientRepository.findByEmail(username);
        if(client.isPresent()){
            return client.get().getId();
        } else {
            System.out.println("Client not found with username: " + username);
            return null;
        }
    }
}
