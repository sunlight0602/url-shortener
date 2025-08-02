// package com.example.demo.entity;
//
// import jakarta.persistence.*;
//
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
//
// import java.util.Collection;
// import java.util.HashSet;
// import java.util.Set;
//
// @Slf4j
// @Entity
// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// public class User implements UserDetails {
//     // Spring Security 的機制會要求你提供一個實作了 UserDetails 介面的物件來代表 經過驗證的使用者，並在登入時放入 SecurityContext 中
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//
//     @Column(nullable = false, unique = true)
//     private String username;
//
//     @Column(nullable = false)
//     private String password;
//
//     @ManyToMany(fetch = FetchType.EAGER)
//     @JoinTable(
//             name = "user_role",
//             joinColumns = @JoinColumn(name = "user_id"),
//             inverseJoinColumns = @JoinColumn(name = "role_name"))
//     private Set<Role> roles;
//
//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         log.info("asdfasdf");
//         Set<GrantedAuthority> authorities = new HashSet<>();
//         for (Role role : roles) {
//             log.info("ROLE_" + role.getName());
//             authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//             for (Permission permission : role.getPermissions()) {
//                 authorities.add(new SimpleGrantedAuthority(permission.getName()));
//             }
//         }
//         return authorities;
//     }
// }
