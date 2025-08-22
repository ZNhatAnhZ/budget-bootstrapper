package com.budgetbootstrapper.file_storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file-storage")
@Data
public class StorageConfig {
    private String pushFileToGitScriptDirectory;
    private String pushFileToGitScriptName;
    private String baseUrl;
    private String localStoragePath;
    private String gitRepositoryUrl;
}
