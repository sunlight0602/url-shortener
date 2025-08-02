// package com.example.demo.entity;
//
// import jakarta.persistence.*;
//
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
//
// import java.util.Set;
//
// @Entity
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class Role {
//
//     @Id private String name; // 例如 "ADMIN", "OPERATOR", "AUDITOR"
//
//     @ManyToMany(fetch = FetchType.EAGER)
//     @JoinTable(
//             name = "role_permission",
//             joinColumns = @JoinColumn(name = "role_name"),
//             inverseJoinColumns = @JoinColumn(name = "permission_name"))
//     private Set<Permission> permissions;
// }
