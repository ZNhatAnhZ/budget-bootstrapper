CREATE TABLE IF NOT EXISTS news
(
    id          UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    category    UUID NULL,
    title       VARCHAR NULL,
    content     TEXT NULL,
    created_on  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    metadata    JSONB NULL,
    external_id VARCHAR NULL,
    CONSTRAINT pk_external_id UNIQUE (external_id)
);

CREATE TABLE IF NOT EXISTS category
(
    id          UUID PRIMARY KEY,
    name        VARCHAR NOT NULL,
    description TEXT,
    created_on  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
    CONSTRAINT  name_unique UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS crawler_job
(
    id           UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    crawler_name VARCHAR,
    content      JSONB,
    state        VARCHAR DEFAULT 'INIT' NOT NULL,
    metadata     JSONB NULL,
    created_on   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);