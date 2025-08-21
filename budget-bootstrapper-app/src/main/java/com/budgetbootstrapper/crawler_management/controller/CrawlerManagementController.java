package com.budgetbootstrapper.crawler_management.controller;

import com.budgetbootstrapper.crawler_management.service.AnimalNewsCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawler-management")
@RequiredArgsConstructor
public class CrawlerManagementController {

    private final AnimalNewsCrawlerService animalNewsCrawlerService;

    @PostMapping("/start-animal-news-crawler")
    public ResponseEntity<Void> startAnimalNewsCrawler() {
        animalNewsCrawlerService.startCrawler();
        return ResponseEntity.accepted().build();
    }
}
