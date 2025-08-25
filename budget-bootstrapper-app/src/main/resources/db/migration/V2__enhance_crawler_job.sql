ALTER TABLE public.crawler_job
    ADD state varchar DEFAULT 'INIT' NOT NULL;
ALTER TABLE public.crawler_job
    ADD metadata jsonb NULL;
ALTER TABLE public.crawler_job ALTER COLUMN "content" TYPE jsonb USING "content"::jsonb::jsonb;
ALTER TABLE public.news ALTER COLUMN "images" TYPE jsonb USING "images"::jsonb::jsonb;