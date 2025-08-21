package com.budgetbootstrapper.crawler_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "crawler_job")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerJob {

    @Id
    private int id;

    private String crawlerName;

    private String content;

    private Long createdOn;
}
