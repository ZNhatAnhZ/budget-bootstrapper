package com.budgetbootstrapper.animal_news.receiver;

import com.budgetbootstrapper.common.dto.AnimalNewsCreationEvent;
import com.budgetbootstrapper.animal_news.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnimalNewsReceiver {

    private final NewsService newsService;

    @ApplicationModuleListener
    void onCreationEvent(AnimalNewsCreationEvent event) {
        log.info("Received AnimalNewsCreationEvent: {}", event);
        try {
            newsService.createNews(event);
        } catch (Exception e) {
            log.error("Failed to process AnimalNewsCreationEvent: {}", event, e);
            throw e;
        }
        log.info("Processed AnimalNewsCreationEvent: {}", event);
    }
}
