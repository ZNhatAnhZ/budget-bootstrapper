package com.budgetbootstrapper.file_storage.receiver;

import com.budgetbootstrapper.common.dto.UploadFileEvent;
import com.budgetbootstrapper.file_storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageEventReceiver {

    @Qualifier("gitStorageService")
    private final StorageService gitStorageService;

    @Qualifier("localStorageService")
    private final StorageService localStorageService;

    @ApplicationModuleListener
    void onUploadFileEvent(UploadFileEvent event) {
        String absolutePath = null;
        log.info("Received UploadFileEvent: {}", event);
        try {
            absolutePath = localStorageService.save(event.getFileName(), event.getDownloadUrl());
            log.info("Uploaded local file at: {}", absolutePath);
            String remotePath = gitStorageService.save(event.getFileName(), absolutePath);
            log.info("Uploaded remote file at: {}", remotePath);
            log.info("Result of the upload to remotePath: {} is: {}", remotePath, gitStorageService.isFileExists(event.getFileName()));
        } catch (Exception e) {
            log.error("Failed to upload file: {}", event.getFileName(), e);
        } finally {
            // Clean up the local file after upload
            localStorageService.delete(absolutePath);
        }
    }
}
