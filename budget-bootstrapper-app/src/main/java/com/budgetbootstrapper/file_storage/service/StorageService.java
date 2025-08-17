package com.budgetbootstrapper.file_storage.service;

import com.budgetbootstrapper.file_storage.config.StorageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Service
public class StorageService {

    private final StorageConfig storageConfig;

    private final RestClient restClient;

    public void uploadFile(String filePath) {
        try {
            pushFileToStorage(filePath);
            verifyFileUploadSuccess(filePath);
        } finally {
            deleteLocalFile(filePath);
        }
    }

    private void deleteLocalFile(String filePath) {
        try {
            log.info("deleting file: {}", filePath);
            // Clean up the file after upload
            boolean result = new File(filePath).delete();
            log.info("File deletion status for {}: {}", filePath, result);
        } catch (Exception e) {
            log.warn("Failed to delete local file: {}", filePath, e);
        }
    }

    private void verifyFileUploadSuccess(String filePath) {
        try {
            String fileName = new File(filePath).getName();
            restClient.get()
                    .uri(storageConfig.getBaseUrl() + fileName)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            String message = "File upload verification failed for file: %s".formatted(filePath);
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private void pushFileToStorage(String filePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("sh", storageConfig.getPushFileToGitScriptName(), filePath); // Command to execute
            pb.directory(new File(storageConfig.getPushFileToGitScriptDirectory())) // Set the current directory
                    .redirectOutput(ProcessBuilder.Redirect.INHERIT) // Redirect output to the console
                    .redirectError(ProcessBuilder.Redirect.INHERIT) // Redirect errors to the console
                    .start()
                    .onExit()
                    .thenAccept(_ -> log.info("uploading {} completed", filePath));
        } catch (Exception e) {
            String message = "Failed to push file to storage: %s".formatted(filePath);
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public void downloadFile(String fileName) {
        // Implementation for downloading a file
    }

    // Example method to delete a file
    public void deleteFile(String fileName) {
        // Implementation for deleting a file
    }
}
