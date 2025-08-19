package com.budgetbootstrapper.crawler_mangement.config;

import com.budgetbootstrapper.crawler_mangement.service.CrawlerExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulingConfig {

    private final CrawlerExternalService crawlerExternalService;

//  @Scheduled(cron = "${crawler.cron-expression}")
//  public void startCrawlerCronJob() {
//    log.info("Running startCrawlerCronJob at {}", Instant.now());
//    crawlerExternalService.startCrawler();
//  }
}
