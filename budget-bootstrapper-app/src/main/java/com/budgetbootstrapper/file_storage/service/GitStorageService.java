package com.budgetbootstrapper.file_storage.service;

import com.budgetbootstrapper.file_storage.config.StorageConfig;
import com.budgetbootstrapper.file_storage.exception.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class GitStorageService implements StorageService {

    private final StorageConfig storageConfig;

    private final RestClient restClient;

    private final LocalStorageService localStorageService;

    // this method is to move the file from local temp directory to git local directory, then there will be a scheduled job to push the files to git repo
    @Override
    public String save(String fileName, String filePathSrc) {
        Path file = Path.of(storageConfig.getLocalGitFileDirectory(), fileName);
        try {
            if (!Files.exists(file.getParent())) {
                Files.createDirectories(file.getParent());
                log.info("Directories created: {}", file.getParent().toAbsolutePath());
            }
            localStorageService.move(filePathSrc, file.toString());
            log.info("File moved to: {}", file.toAbsolutePath());
            return storageConfig.getBaseUrl() + fileName;
        } catch (Exception e) {
            String message = "Failed to push file to storage: %s".formatted(filePathSrc);
            log.error(message, e);
            throw new StorageException(message, e);
        }
    }

    // this method is to execute the shell script to push files to git repo
    public void pushFilesToGit() {
        try {
            ProcessBuilder pb = new ProcessBuilder("sh", ResourceUtils.getFile(storageConfig.getPushFileToGitScriptName()).getAbsolutePath(),
                    storageConfig.getGitRepositoryUrl()); // Command to execute
            pb.directory(Path.of(storageConfig.getLocalGitFileDirectory()).toFile()) // Set the current directory
                    .redirectOutput(ProcessBuilder.Redirect.INHERIT) // Redirect output to the console
                    .redirectError(ProcessBuilder.Redirect.INHERIT) // Redirect errors to the console
                    .start()
                    .onExit()
                    .thenAccept(_ -> log.info("uploading to git completed"));
        } catch (IOException e) {
            log.error("Failed to execute git push script", e);
        }
    }

    @Override
    public void read(String filename, String filePath) {
        // not implemented
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
        // not implemented
    }

    @Override
    public String move(String sourcePath, String destinationPath) {
        // not implemented
        return "";
    }
}
