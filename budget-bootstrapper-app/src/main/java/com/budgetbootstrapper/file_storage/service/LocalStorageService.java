package com.budgetbootstrapper.file_storage.service;

import com.budgetbootstrapper.file_storage.config.StorageConfig;
import com.budgetbootstrapper.file_storage.exception.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocalStorageService implements StorageService {

    private final StorageConfig storageConfig;

    @Override
    public String save(String filename, String filePathSrc) {
        Path file = Path.of(storageConfig.getLocalStoragePath(), filename);
        try {
            if (!Files.exists(file.getParent())) {
                Files.createDirectories(file.getParent());
                log.info("Parent directories created: {}", file.getParent().toAbsolutePath());
            }
            Files.createFile(file);
            log.info("File created: {}", file.toAbsolutePath());
        } catch (Exception e) {
            String message = "Failed to create file: %s in path: %s".formatted(filename, storageConfig.getLocalStoragePath());
            log.error(message, e);
            throw new StorageException(message, e);
        }

        try (ReadableByteChannel rbc = Channels.newChannel(new URI(filePathSrc).toURL().openStream());
             FileOutputStream fos = new FileOutputStream(file.toFile())) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return file.toAbsolutePath().toString();
        } catch (Exception e) {
            String message = "Failed to save file: %s to path: %s".formatted(filename, storageConfig.getLocalStoragePath());
            log.error(message, e);
            throw new StorageException(message, e);
        }
    }

    @Override
    public void read(String filename, String filePath) {
    }

    @Override
    public boolean isFileExists(String filePath) {
        return false;
    }

    @Override
    public void delete(String filePath) {
        try {
            log.info("deleting file: {}", filePath);
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                log.warn("File not found: {}", path);
                return;
            }
            var success = Files.deleteIfExists(path);
            log.info("File deletion status for {}: {}", path, success);
        } catch (Exception e) {
            log.warn("Failed to delete local file: {}", filePath, e);
        }
    }

    @Override
    public String move(String sourcePath, String destinationPath) {
        try {
            return Files.move(Path.of(sourcePath), Path.of(destinationPath)).toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("Failed to move file from {} to {}", sourcePath, destinationPath, e);
            throw new StorageException(e);
        }
    }
}
