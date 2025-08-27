package com.budgetbootstrapper.file_storage.service;

import com.budgetbootstrapper.file_storage.config.StorageConfig;
import com.budgetbootstrapper.file_storage.exception.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocalStorageService implements StorageService {

    private final StorageConfig storageConfig;

    @Override
    public String save(String filename, String filePathSrc) {
        File file = new File(storageConfig.getLocalStoragePath(), filename);

        try {
            File parentDir = file.getParentFile();
            if (parentDir == null || !parentDir.exists()) {
                boolean dirsCreated = parentDir.mkdirs();
                if (dirsCreated) {
                    log.info("Parent directories created: " + parentDir.getAbsolutePath());
                } else {
                    log.info("Failed to create parent directories: " + parentDir.getAbsolutePath());
                    throw new StorageException("Failed to create parent directories: " + parentDir.getAbsolutePath());
                }
            }
            boolean result = file.createNewFile();
            if (result) {
                log.info("File created: " + file.getAbsolutePath());
            } else {
                log.info("File already exists: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            String message = "Failed to create file: %s in path: %s".formatted(filename, storageConfig.getLocalStoragePath());
            log.error(message, e);
            throw new StorageException(message, e);
        }

        try (ReadableByteChannel rbc = Channels.newChannel(new URI(filePathSrc).toURL().openStream());
             FileOutputStream fos = new FileOutputStream(file)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return file.getAbsolutePath();
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
            // Clean up the file after upload
            boolean result = new File(filePath).delete();
            log.info("File deletion status for {}: {}", filePath, result);
        } catch (Exception e) {
            log.warn("Failed to delete local file: {}", filePath, e);
        }
    }
}
