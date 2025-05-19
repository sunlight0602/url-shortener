package com.example.demo.config;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 初始化 Permission
            Permission read = new Permission("READ_REPORTS");
            Permission write = new Permission("WRITE_REPORTS");
            Permission manage = new Permission("MANAGE_USERS");

            permissionRepository.saveAll(Set.of(read, write, manage));

            // 初始化 Role
            Role admin = new Role("ADMIN", Set.of(read, write, manage));
            Role operator = new Role("OPERATOR", Set.of(read, write));
            Role auditor = new Role("AUDITOR", Set.of(read));

            roleRepository.saveAll(Set.of(admin, operator, auditor));

            // 初始化 User
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("password123"));
                user.setRoles(Set.of(admin)); // 預設 admin 角色
                userRepository.save(user);
            }
        };
    }
}
