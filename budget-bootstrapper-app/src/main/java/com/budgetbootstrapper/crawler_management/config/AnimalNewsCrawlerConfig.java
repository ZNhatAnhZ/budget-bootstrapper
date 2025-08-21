package com.budgetbootstrapper.crawler_management.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "crawler-management.animal-news")
@Data
public class AnimalNewsCrawlerConfig {
    private Map<String, String> args;
    private String scriptDirectory;
    private String cronExpression;
}
