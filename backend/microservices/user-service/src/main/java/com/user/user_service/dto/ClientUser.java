package com.user.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUser {
    private Integer userId;
    private String name;
    private String email;
    private String password;
}
