package com.budgetbootstrapper.crawler_management.mapper;

import com.budgetbootstrapper.common.dto.AnimalNewsCreationEvent;
import com.budgetbootstrapper.crawler_management.entity.CrawlerJob;
import io.hypersistence.utils.hibernate.type.json.internal.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class AnimalNewsCrawlerJobMapper {

    public AnimalNewsCreationEvent toAnimalNewsCreationEvent(CrawlerJob job) {
        try {
            return JacksonUtil.fromString(job.getContent(), AnimalNewsCreationEvent.class);
        } catch (Exception e) {
            log.error("failed to map CrawlerJob to AnimalNewsCreationEvent", e);
            return null;
        }
    }
}