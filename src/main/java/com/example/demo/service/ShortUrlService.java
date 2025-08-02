package com.example.demo.service;

import com.example.demo.entity.ShortUrlEntity;
import com.example.demo.repository.ShortUrlRepository;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service // Marks this class as a Spring service component
@RequiredArgsConstructor // Lombok annotation to generate a constructor with final fields
@Slf4j // Lombok annotation for logging
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    /**
     * Demonstrates @Transactional:
     * This method will execute within a transaction. If any runtime exception occurs,
     * the transaction will be rolled back.
     * It simply finds a ShortUrlEntity by its shortCode.
     *
     * @param shortCode The short code to search for.
     * @return An Optional containing the ShortUrlEntity if found.
     */
    // @Transactional(readOnly = true) // readOnly = true can be an optimization for read operations
    public Optional<ShortUrlEntity> getShortUrlByShortCode(String shortCode) {
        log.info("Attempting to retrieve ShortUrl for shortCode: {}", shortCode);
        return shortUrlRepository.findByShortCode(shortCode);
    }

    /**
     * Demonstrates @Transactional and @Lock(PESSIMISTIC_WRITE):
     * This method will:
     * 1. Acquire a pessimistic write lock on the ShortUrlEntity identified by the shortCode.
     * This means no other transaction can read or write this specific row until this transaction commits or rolls back.
     * 2. Simulate a long-running operation (e.g., complex business logic, external API call).
     * 3. Update the longUrl of the entity.
     * 4. Save the changes, which commits the transaction and releases the lock.
     * <p>
     * If another transaction tries to acquire a lock on the same row, it will wait.
     * If the wait time exceeds the database's lock timeout (or Spring's default), it will throw an exception.
     *
     * @param shortCode   The short code of the entity to update.
     * @param newLongUrl  The new long URL to set.
     * @param delayMillis A simulated delay in milliseconds to make the lock observable.
     * @return The updated ShortUrlEntity, or Optional.empty() if not found or update fails.
     */
    @Transactional // This transaction will acquire and release the lock
    @SneakyThrows
    public Optional<ShortUrlEntity> updateLongUrlWithPessimisticLock(String shortCode, String newLongUrl, long delayMillis) {
        try {
            ShortUrlEntity shortUrlEntity = shortUrlRepository.findByShortCode(shortCode).orElseThrow();
            String oldLongUrl = shortUrlEntity.getLongUrl();
            log.info("Lock acquired for shortCode: {}. Current longUrl: {}", shortCode, oldLongUrl);

            log.info("Simulating delay of {}ms for shortCode: {}", delayMillis, shortCode);
            TimeUnit.MILLISECONDS.sleep(delayMillis);

            shortUrlEntity.setLongUrl(newLongUrl);
            shortUrlRepository.save(shortUrlEntity);
            log.info("Updated shortCode: {} from '{}' to '{}'. Lock released.", shortCode, oldLongUrl, newLongUrl);
            return Optional.of(shortUrlEntity);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            log.error("Thread interrupted during simulated delay for shortCode: {}", shortCode, e);
            // Transaction will likely be rolled back due to this exception
            throw new RuntimeException("Simulated delay interrupted", e);
        } catch (CannotAcquireLockException | PessimisticLockException | LockTimeoutException e) {
            log.error("Failed to acquire pessimistic lock for shortCode: {}. Another transaction might be holding the lock.", shortCode, e);
            // This exception will cause the transaction to roll back
            throw e; // Re-throw to propagate the error
        } catch (OptimisticLockingFailureException e) {
            log.error("Optimistic locking failure for shortCode: {}. Data was modified by another transaction concurrently.", shortCode, e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred during update for shortCode: {}", shortCode, e);
            throw new RuntimeException("Update failed due to unexpected error", e);
        }
    }

    /**
     * A simple method to create a new ShortUrlEntity.
     * Used for seeding data for the experiment.
     *
     * @param shortUrlEntity The entity to save.
     * @return The saved entity.
     */
    @Transactional
    public ShortUrlEntity createShortUrl(ShortUrlEntity shortUrlEntity) {
        log.info("Creating new ShortUrl: {}", shortUrlEntity.getShortCode());
        return shortUrlRepository.save(shortUrlEntity);
    }
}
