package com.budgetbootstrapper.animal_news.service;

import com.budgetbootstrapper.animal_news.entity.News;
import com.budgetbootstrapper.animal_news.exception.ResourceNotFoundException;
import com.budgetbootstrapper.animal_news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @Value("${news.image-url-prefix}")
    private final String imageUrlPrefix;

    public Page<News> getAllPartialNews(int page, int size) {
        Page<News> pageList =
                newsRepository.findAll(PageRequest.of(page, size, Sort.by("createdOn").descending()));

        return pageList.map(
                e -> {
                    e.setImages(List.of(e.getImages().getFirst()));
                    e.setCreatedOn(e.getCreatedOn());
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

    private void convertImageUrlUsingThePrefix(News e) {
        e.setImages(e.getImages().stream().map(image -> imageUrlPrefix + image).toList());
    }
}
