package com.budgetbootstrapper.file_storage.service;

import com.budgetbootstrapper.file_storage.config.StorageConfig;
import com.budgetbootstrapper.file_storage.exception.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.io.File;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class GitStorageService implements StorageService {

    private final StorageConfig storageConfig;

    private final RestClient restClient;

    @Override
    public String save(String fileName, String filePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("sh", storageConfig.getPushFileToGitScriptName(),
                    storageConfig.getGitRepositoryUrl(), filePath); // Command to execute
            pb.directory(new File(storageConfig.getPushFileToGitScriptDirectory())) // Set the current directory
                    .redirectOutput(ProcessBuilder.Redirect.INHERIT) // Redirect output to the console
                    .redirectError(ProcessBuilder.Redirect.INHERIT) // Redirect errors to the console
                    .start()
                    .onExit()
                    .thenAccept(_ -> log.info("uploading {} completed", filePath));
            return storageConfig.getBaseUrl() + fileName;
        } catch (Exception e) {
            String message = "Failed to push file to storage: %s".formatted(filePath);
            log.error(message, e);
            throw new StorageException(message, e);
        }
    }

    @Override
    public void read(String filename, String filePath) {

    }

    @Override
    public boolean isFileExists(String fileName) {
        try {
            restClient.get()
                    .uri(storageConfig.getBaseUrl() + fileName)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException e) {
            if (NOT_FOUND.isSameCodeAs(e.getStatusCode())) {
                return false;
            }
            String message = "Error checking file existence in storage: %s".formatted(fileName);
            log.error(message, e);
            throw new StorageException(message, e);
        } catch (Exception e) {
            String message = "Error checking file existence in storage: %s".formatted(fileName);
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
        return true;
    }

    @Override
    public void delete(String filePath) {

    }
}
