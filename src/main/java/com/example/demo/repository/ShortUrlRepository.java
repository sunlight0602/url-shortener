package com.example.demo.repository;

import com.example.demo.entity.ShortUrlEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Date;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
    // This method, when called within a @Transactional context,
    // will attempt to acquire a pessimistic write lock on the found entity.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ShortUrlEntity> findByShortCode(String shortCode);

    Optional<ShortUrlEntity> findByLongUrl(String shortCode);

    void deleteByCreatedAtBefore(Date expiryTime);
}
