package com.budgetbootstrapper.crawler_management.mapper;

import com.budgetbootstrapper.common.dto.AnimalNewsCreationEvent;
import com.budgetbootstrapper.crawler_management.entity.CrawlerJob;
import io.hypersistence.utils.hibernate.type.json.internal.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.BeanUtils;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class AnimalNewsCrawlerJobMapper {

    public abstract AnimalNewsCreationEvent toAnimalNewsCreationEvent(CrawlerJob job);

    @AfterMapping
    private void afterMappingToAnimalNewsCreationEvent(CrawlerJob job, @MappingTarget AnimalNewsCreationEvent animalNewsCreationEvent) {
        try {
            AnimalNewsCreationEvent event = JacksonUtil.fromString(job.getContent(), AnimalNewsCreationEvent.class);
            BeanUtils.copyProperties(event, animalNewsCreationEvent);
        } catch (Exception e) {
            log.error("failed to map CrawlerJob to AnimalNewsCreationEvent", e);
        }
    }
}