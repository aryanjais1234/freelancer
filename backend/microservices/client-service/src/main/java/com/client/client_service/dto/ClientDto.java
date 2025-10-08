package com.client.client_service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientDto {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private List<Integer> projectIds = new ArrayList<>();
}
