package com.budgetbootstrapper.file_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileEvent {
    String filePath;
}
