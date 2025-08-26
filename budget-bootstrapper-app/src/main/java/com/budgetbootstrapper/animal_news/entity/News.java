package com.budgetbootstrapper.animal_news.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity(name = "news")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    public static final String NEWS_METADATA_IMAGES = "images";

    public static final String NEWS_CREATED_ON = "createdOn";

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String title;

    private String date;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, Object> metadata;

    private String content;

    @CreatedDate
    private Instant createdOn;

    @Column(name = "external_id", length = Integer.MAX_VALUE)
    private String externalId;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

}
