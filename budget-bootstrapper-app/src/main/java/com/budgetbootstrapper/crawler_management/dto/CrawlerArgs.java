package com.budgetbootstrapper.crawler_management.dto;

import lombok.Data;

@Data
public class CrawlerArgs {
    private String crawlerName;
    private String host;
    private Integer port;
    private String user;
    private String password;
    private String database;
    private Integer connectionLimit;
    private Integer maxIndex;
    private String targetUrl;
}
