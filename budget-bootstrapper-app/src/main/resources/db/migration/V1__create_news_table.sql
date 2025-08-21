CREATE TABLE news
(
    id         INT AUTO_INCREMENT NOT NULL,
    title VARCHAR NULL,
    date  VARCHAR NULL,
    images     JSON NULL,
    content    TEXT NULL,
    created_on INT NULL,
    CONSTRAINT pk_news PRIMARY KEY (id)
);

CREATE TABLE crawler_job
(
    id           INTEGER NOT NULL,
    crawler_name VARCHAR,
    content      VARCHAR,
    created_on   INT,
    CONSTRAINT pk_crawler_job PRIMARY KEY (id)
);