package com.budgetbootstrapper.crawler_management.config;

import com.budgetbootstrapper.crawler_management.service.AnimalNewsCrawlerService;
import com.budgetbootstrapper.crawler_management.service.CrawlerJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulingConfig {

    private final AnimalNewsCrawlerService animalNewsCrawlerService;

    private final CrawlerJobService crawlerJobService;

    @Scheduled(cron = "${crawler-management.jobs.cron-expression}")
    public void startJobProcessing() {
        log.info("Running startAnimalNewsCrawler at {}", Instant.now());
        animalNewsCrawlerService.startCrawler();
    }

    @Scheduled(cron = "${crawler-management.animal-news.cron-expression}")
    public void startAnimalNewsCrawler() {
        log.info("Running startAnimalNewsCrawler at {}", Instant.now());
        animalNewsCrawlerService.startCrawler();
    }
}
