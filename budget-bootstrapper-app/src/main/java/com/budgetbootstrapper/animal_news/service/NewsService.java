package com.budgetbootstrapper.animal_news.service;

import com.budgetbootstrapper.animal_news.entity.News;
import com.budgetbootstrapper.animal_news.exception.ResourceNotFoundException;
import com.budgetbootstrapper.animal_news.repository.NewsRepository;
import com.budgetbootstrapper.common.dto.AnimalNewsCreationEvent;
import com.budgetbootstrapper.common.dto.UploadFileEvent;
import io.hypersistence.utils.hibernate.type.json.internal.JacksonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.budgetbootstrapper.animal_news.entity.News.NEWS_CREATED_ON;
import static com.budgetbootstrapper.animal_news.entity.News.NEWS_METADATA_IMAGES;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${animal-news.image-url-prefix}")
    private final String imageUrlPrefix;

    public Page<News> getAllPartialNews(int page, int size) {
        Page<News> pageList =
                newsRepository.findAll(PageRequest.of(page, size, Sort.by(NEWS_CREATED_ON).descending()));

        return pageList.map(
                e -> {
                    getTitleNewsImageName(e);
                    e.setContent(e.getContent().substring(0, 400));
                    convertImageUrlUsingThePrefix(e);
                    return e;
                });
    }

    public News getNewsDetail(String id) {
        return newsRepository
                .findById(id)
                .map(
                        e -> {
                            convertImageUrlUsingThePrefix(e);
                            return e;
                        })
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        String.format("Can not find the news with id: %s", id)));
    }

    public void createNews(AnimalNewsCreationEvent event) {
        event.getImages().forEach((fileName, downloadUrl) -> applicationEventPublisher.publishEvent(new UploadFileEvent(fileName, downloadUrl)));
        Map<String, Object> metadata = new HashMap<>();
        metadata.put(NEWS_METADATA_IMAGES, new ArrayList<>(event.getImages().keySet()));
        newsRepository.save(News.builder()
                .id(UUID.randomUUID())
                .title(event.getTitle())
                .date(event.getDate())
                .metadata(metadata)
                .content(event.getContent())
                .createdOn(event.getCreatedOn())
                .externalId(String.valueOf(event.getId()))
                .build());
    }

    @SuppressWarnings("unchecked")
    private void convertImageUrlUsingThePrefix(News e) {
        e.setMetadata(Optional.ofNullable(e.getMetadata())
                .map(metadata -> metadata.get(NEWS_METADATA_IMAGES))
                .map(Object::toString)
                .map(imageListStr -> JacksonUtil.fromString(imageListStr, List.class))
                .map(imageNameList -> (List<String>) imageNameList)
                .map(imageList -> imageList.stream().map(imageName -> imageUrlPrefix + imageName).toList())
                .map(imageUrlList -> {
                    Map<String, Object> metadata = new HashMap<>();
                    metadata.put(NEWS_METADATA_IMAGES, imageUrlList);
                    return metadata;
                }).orElse(Map.of())
        );
    }

    @SuppressWarnings("unchecked")
    private void getTitleNewsImageName(News e) {
        Map<String, Object> metadata = e.getMetadata();
        List<String> images = JacksonUtil.fromString(e.getMetadata().get(NEWS_METADATA_IMAGES).toString(), List.class);
        metadata.put(NEWS_METADATA_IMAGES, images.getFirst());
        e.setMetadata(metadata);
    }
}
