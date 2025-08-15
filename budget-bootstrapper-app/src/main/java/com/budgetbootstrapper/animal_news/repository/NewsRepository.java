package com.budgetbootstrapper.animal_news.repository;

import com.budgetbootstrapper.animal_news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, String> {
}
