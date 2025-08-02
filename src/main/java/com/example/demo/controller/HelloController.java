package com.example.demo.controller;

import com.example.demo.entity.ShortUrlEntity;
import com.example.demo.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/v1/shorturls") // Base path for these endpoints
@RequiredArgsConstructor // Lombok annotation to generate a constructor with final fields
@Slf4j // Lombok annotation for logging
public class HelloController { // Renamed from HelloController to better reflect its purpose, or you can keep it as is.

    private final ShortUrlService shortUrlService; // Inject the new service

    @GetMapping("/hello") // Original health check endpoint
    public String checkHealth() {
        return "ok";
    }

    /**
     * Endpoint to create a new ShortUrlEntity for testing.
     * http://localhost:8080/api/v1/shorturls/create?longUrl=https://example.com/original&shortCode=testcode
     */
    @PostMapping("/create")
    public ResponseEntity<ShortUrlEntity> createShortUrl(@RequestParam String longUrl, @RequestParam String shortCode) {
        ShortUrlEntity newEntity = ShortUrlEntity.builder()
                .longUrl(longUrl)
                .shortCode(shortCode)
                .createdAt(LocalDateTime.now()) // Set creation time
                .build();
        ShortUrlEntity savedEntity = shortUrlService.createShortUrl(newEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    /**
     * Endpoint to get a ShortUrlEntity by shortCode.
     * http://localhost:8080/api/v1/shorturls/{shortCode}
     */
    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortUrlEntity> getShortUrl(@PathVariable String shortCode) {
        Optional<ShortUrlEntity> entity = shortUrlService.getShortUrlByShortCode(shortCode);
        return entity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to demonstrate pessimistic locking.
     * Call this endpoint multiple times concurrently with the same shortCode
     * to observe locking behavior.
     * <p>
     * Example:
     * 1. First call (will acquire lock and delay):
     * http://localhost:8080/api/v1/shorturls/update-locked/mycode?newLongUrl=https://newurl1.com&delay=5000
     * 2. Second call (immediately after first, with same 'mycode'):
     * http://localhost:8080/api/v1/shorturls/update-locked/mycode?newLongUrl=https://newurl2.com&delay=100
     * <p>
     * You should see the second call wait until the first one finishes,
     * or potentially throw a lock timeout exception if the database's timeout is shorter.
     */
    @PutMapping("/update-locked/{shortCode}")
    public ResponseEntity<?> updateShortUrlWithLock(
            @PathVariable String shortCode,
            @RequestParam String newLongUrl,
            @RequestParam(defaultValue = "0") long delay) {
        try {
            Optional<ShortUrlEntity> updatedEntity = shortUrlService.updateLongUrlWithPessimisticLock(shortCode, newLongUrl, delay);
            return updatedEntity.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error updating short URL with lock for {}: {}", shortCode, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
