package com.budgetbootstrapper.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileEvent {
    private String fileName;
    private String downloadUrl;
}
