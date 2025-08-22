package com.budgetbootstrapper.crawler_management.service;

import com.budgetbootstrapper.crawler_management.mapper.AnimalNewsCrawlerJobMapper;
import com.budgetbootstrapper.crawler_management.repository.CrawlerJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlerJobService {

    private final CrawlerJobRepository crawlerJobRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final AnimalNewsCrawlerJobMapper animalNewsCrawlerJobMapper;

    @Value("${crawler-management.jobs.limit}")
    private final int limit;

    @Transactional
    public void startJobProcessing() {
        log.info("Starting job processing");
        crawlerJobRepository.getCrawlerJobs(limit).forEach(job -> {
            log.info("Processing job: {}", job.getId());
            applicationEventPublisher.publishEvent(animalNewsCrawlerJobMapper.toAnimalNewsCreationEvent(job));
            log.info("Published event for job: {}", job.getId());
            crawlerJobRepository.delete(job);
            log.info("Deleted job: {}", job.getId());
        });
        log.info("Job processing completed");
    }
}
