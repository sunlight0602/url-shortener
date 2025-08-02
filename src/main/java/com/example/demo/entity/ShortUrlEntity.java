package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor; // Import AllArgsConstructor
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; // Import NoArgsConstructor
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor // Added: Generates a no-argument constructor, required by JPA
@AllArgsConstructor // Added: Generates a constructor with all fields, useful when @Builder is present
@Entity
public class ShortUrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shortCode;

    @Column(nullable = false)
    private String longUrl;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
