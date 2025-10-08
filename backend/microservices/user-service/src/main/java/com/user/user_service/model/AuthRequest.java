package com.user.user_service.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String userName;
    private String password;
}
