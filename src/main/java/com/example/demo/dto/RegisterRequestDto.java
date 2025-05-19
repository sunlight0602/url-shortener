package com.example.demo.dto;

import com.example.demo.entity.Role;
import lombok.Data;

@Data
public class RegisterRequestDto {
    private String username;
    private String password;
    private Role role;
}
