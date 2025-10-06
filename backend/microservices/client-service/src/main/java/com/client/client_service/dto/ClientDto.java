package com.client.client_service.dto;

import lombok.Data;

@Data
public class ClientDto {
    private Integer userId;
    private String name;
    private String email;
    private String password;
}
