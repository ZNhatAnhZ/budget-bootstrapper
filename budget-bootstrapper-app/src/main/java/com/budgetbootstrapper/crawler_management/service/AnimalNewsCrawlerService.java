package com.budgetbootstrapper.crawler_management.service;

import com.budgetbootstrapper.crawler_management.config.AnimalNewsCrawlerConfig;
import com.budgetbootstrapper.crawler_management.dto.CrawlerArgs;
import io.hypersistence.utils.hibernate.type.json.internal.JacksonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimalNewsCrawlerService {

    private final AnimalNewsCrawlerConfig animalNewsCrawlerConfig;

    public void startCrawler(CrawlerArgs args) {
        try {
            ProcessBuilder pb =
                    new ProcessBuilder(
                            "node",
                            "animal_news_1.js",
                            JacksonUtil.toString(args)); // Command to execute
            pb.directory(new File(animalNewsCrawlerConfig.getScriptDirectory())) // Set current directory
                    .redirectOutput(ProcessBuilder.Redirect.INHERIT) // Redirect output to console
                    .redirectError(ProcessBuilder.Redirect.INHERIT) // Redirect errors to console
                    .start()
                    .onExit()
                    .thenAccept(_ -> log.info("startCrawler process completed"));
        } catch (IOException e) {
            log.error("Error while running startCrawler", e);
        }
    }
}
