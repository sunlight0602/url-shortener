// package com.example.demo.controller;
//
// import com.example.demo.dto.MessageResponseDto;
// import com.example.demo.service.UrlShortenerService;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// @Slf4j
// @RestController
// public class UrlShortenerController {
//     private final UrlShortenerService urlShortenerService;
//
//     public UrlShortenerController(UrlShortenerService urlShortenerService) {
//         this.urlShortenerService = urlShortenerService;
//     }
//
//     @GetMapping("health-check/")
//     public String checkHealth() {
//         return "ok";
//     }
//
//     @PostMapping("shorten/")
//     public String shortenUrl(@RequestParam String longUrl) {
//         return this.urlShortenerService.shortenUrl(longUrl);
//     }
//
//     @GetMapping("/debug-auth")
//     public ResponseEntity<?> debugAuth() {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         log.info("Auth class: {}", auth.getPrincipal().getClass().getName());
//         log.info("qwerqwer");
//         return ResponseEntity.ok(auth.getAuthorities());
//     }
//
//     @PreAuthorize("hasRole('ADMIN')")
//     @GetMapping("admin-only/")
//     public String adminOnly() {
//         return "admin only";
//     }
//
//     @PreAuthorize("hasAuthority('MANAGE_USERS')")
//     @GetMapping("/manage-users")
//     public ResponseEntity<?> manageUsers() {
//         return ResponseEntity.ok(new MessageResponseDto("manage-users"));
//     }
//
//     @PreAuthorize("hasAuthority('READ_REPORTS')")
//     @GetMapping("/read-reports")
//     public ResponseEntity<?> readReports() {
//         return ResponseEntity.ok(new MessageResponseDto("read-reports"));
//     }
// }
