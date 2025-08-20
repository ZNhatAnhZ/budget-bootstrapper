package com.budgetbootstrapper.crawler_mangement.service;

import com.budgetbootstrapper.crawler_mangement.config.CrawlerConfig;
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

    //note
    //2nd apptoach: the crawler script will call api to save the records into the database, no db connection needed in the script.
        // pros: the script is only responsible for crawling, the api is responsible for saving to the database.
                // greater scalability as there will be a separate backend module for the scrawler. the model will be n crawler scripts - 1 backend scrawler module
        // cons: the script should be able to call the api, so it should have internet connection.

    private final CrawlerConfig crawlerConfig;

    public void startCrawler() {
        try {
            ProcessBuilder pb =
                    new ProcessBuilder(
                            "node",
                            "index.js",
                            JacksonUtil.toString(crawlerConfig.getArgs())); // Command to execute
            pb.directory(new File(crawlerConfig.getScriptDirectory())) // Set current directory
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
