package com.budgetbootstrapper.file_storage.config;

import com.budgetbootstrapper.file_storage.service.GitStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class FileStorageSchedulingConfig {

    private final GitStorageService gitStorageService;

    @Scheduled(cron = "${file-storage.push-file-to-git-cron-expression}")
    public void startUploadingFileToGitJob() {
        log.info("Running startUploadingFileToGitJob at {}", Instant.now());
        gitStorageService.pushFilesToGit();
    }
}
