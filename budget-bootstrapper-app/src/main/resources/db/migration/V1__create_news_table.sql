CREATE TABLE news
(
    id         INTEGER NOT NULL,
    title VARCHAR NULL,
    date  VARCHAR NULL,
    images     JSON NULL,
    content    TEXT NULL,
    created_on TIMESTAMP NULL,
    CONSTRAINT pk_news PRIMARY KEY (id)
);

CREATE TABLE crawler_job
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    crawler_name VARCHAR,
    content      JSON,
    created_on TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);