package com.budgetbootstrapper.animal_news.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "crawler")
@Data
public class CrawlerConfig {
    private Map<String, String> args;
    private String scriptDirectory;
    private String pushingToGitScriptDirectory;
    private String cronExpression;
}
