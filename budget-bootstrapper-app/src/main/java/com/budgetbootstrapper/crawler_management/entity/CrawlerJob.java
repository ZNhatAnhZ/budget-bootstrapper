package com.budgetbootstrapper.crawler_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity(name = "crawler_job")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerJob {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "crawler_name", length = Integer.MAX_VALUE)
    private String crawlerName;

    @Column(name = "content")
    @JdbcTypeCode(SqlTypes.JSON)
    private String content;

    @NotNull
    @ColumnDefault("'INIT'")
    @Column(name = "state", nullable = false, length = Integer.MAX_VALUE)
    private CrawlerJobState state;

    @Column(name = "metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_on")
    private OffsetDateTime createdOn;

    public enum CrawlerJobState {
        INIT,
        SUCCESS,
        FAILED
    }

}
