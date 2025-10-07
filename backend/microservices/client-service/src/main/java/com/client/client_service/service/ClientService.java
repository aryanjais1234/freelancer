package com.client.client_service.service;

import com.client.client_service.dao.ClientRepository;
import com.client.client_service.dto.ClientDto;
import com.client.client_service.dto.Project;
import com.client.client_service.dto.ProjectResponse;
import com.client.client_service.feign.ProjectInterface;
import com.client.client_service.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ClientDto createClient(ClientDto client) {
        try {
            Client newClient = new Client();
            mapToClient(client, newClient);
            clientRepository.save(newClient);
            return client;
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

    public ProjectResponse createProject(Project project) {
        return projectInterface.createProject(project);
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
