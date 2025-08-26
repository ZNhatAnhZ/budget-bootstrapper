package com.budgetbootstrapper.crawler_management.config;

import com.budgetbootstrapper.crawler_management.dto.CrawlerArgs;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "crawler-management.animal-news")
@Data
public class AnimalNewsCrawlerConfig {
    private CrawlerArgs args;
    private String scriptDirectory;
    private String cronExpression;

    public CrawlerArgs overrideCrawlerArgs(CrawlerArgs args) {
        return Optional.ofNullable(args).map(crawlerArgs -> {
            crawlerArgs.setCrawlerName(Optional.ofNullable(crawlerArgs.getCrawlerName()).orElse(this.args.getCrawlerName()));
            crawlerArgs.setHost(Optional.ofNullable(crawlerArgs.getHost()).orElse(this.args.getHost()));
            crawlerArgs.setPort(Optional.ofNullable(crawlerArgs.getPort()).orElse(this.args.getPort()));
            crawlerArgs.setUser(Optional.ofNullable(crawlerArgs.getUser()).orElse(this.args.getUser()));
            crawlerArgs.setPassword(Optional.ofNullable(crawlerArgs.getPassword()).orElse(this.args.getPassword()));
            crawlerArgs.setDatabase(Optional.ofNullable(crawlerArgs.getDatabase()).orElse(this.args.getDatabase()));
            crawlerArgs.setConnectionLimit(Optional.ofNullable(crawlerArgs.getConnectionLimit()).orElse(this.args.getConnectionLimit()));
            crawlerArgs.setMaxIndex(Optional.ofNullable(crawlerArgs.getMaxIndex()).orElse(this.args.getMaxIndex()));
            crawlerArgs.setTargetUrl(Optional.ofNullable(crawlerArgs.getTargetUrl()).orElse(this.args.getTargetUrl()));
            return crawlerArgs;
        }).orElse(this.args);
    }
}
