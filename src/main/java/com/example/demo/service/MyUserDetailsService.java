// package com.example.demo.service;
//
// import com.example.demo.entity.Role;
// import com.example.demo.entity.User;
// import com.example.demo.repository.UserRepository;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
//
// import java.util.HashSet;
// import java.util.Set;
//
// import javax.swing.*;
//
// @Service
// public class MyUserDetailsService implements UserDetailsService {
//
//     @Autowired private UserRepository userRepository;
//
//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         // Spring Security 的核心元件 AuthenticationManager 最終會透過 UserDetailsService 的
//         // loadUserByUsername(...) 方法來載入使用者，而這個方法必須回傳一個 UserDetails
//         User user =
//                 userRepository
//                         .findByUsername(username)
//                         .orElseThrow(() -> new UsernameNotFoundException("找不到這個 username"));
//
//         Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//         for (Role role : user.getRoles()) {
//             authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//             role.getPermissions()
//                     .forEach(perm -> authorities.add(new SimpleGrantedAuthority(perm.getName())));
//         }
//
//         return new org.springframework.security.core.userdetails.User(
//                 user.getUsername(), user.getPassword(), authorities // 若沒有角色，可傳空清單
//                 );
//     }
// }
