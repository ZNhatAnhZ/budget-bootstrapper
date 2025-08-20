package com.budgetbootstrapper.crawler_mangement.config;

import com.budgetbootstrapper.crawler_mangement.service.AnimalNewsCrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulingConfig {

    private final AnimalNewsCrawlerService crawlerExternalService;

//  @Scheduled(cron = "${crawler.cron-expression}")
//  public void startCrawlerCronJob() {
//    log.info("Running startCrawlerCronJob at {}", Instant.now());
//    crawlerExternalService.startCrawler();
//  }
}
