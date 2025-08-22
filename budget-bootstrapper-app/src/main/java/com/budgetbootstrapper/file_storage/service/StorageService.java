package com.budgetbootstrapper.file_storage.service;

public interface StorageService {
    String save(String filename, String filePathSrc);

    void read(String filename, String filePath);

    boolean isFileExists(String filePath);

    void delete(String filePath);
}
