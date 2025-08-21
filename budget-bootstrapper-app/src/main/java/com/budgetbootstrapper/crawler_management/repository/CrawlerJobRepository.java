package com.budgetbootstrapper.crawler_management.repository;

import com.budgetbootstrapper.crawler_management.entity.CrawlerJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CrawlerJobRepository extends Repository<CrawlerJob, Integer> {


    @Transactional
    @Query(name = "SELECT * from crawler_job LIMIT ?1 FOR UPDATE SKIP LOCKED", nativeQuery = true)
    List<CrawlerJob> getCrawlerJobs(int limit);
}