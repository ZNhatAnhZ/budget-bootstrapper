package com.budgetbootstrapper.file_storage.receiver;

import com.budgetbootstrapper.file_storage.dto.UploadFileEvent;
import com.budgetbootstrapper.file_storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageEventReceiver {

    private final StorageService storageService;

    @ApplicationModuleListener
    void onUploadFileEvent(UploadFileEvent event) {
        storageService.uploadFile(event.getFilePath());
    }
}
