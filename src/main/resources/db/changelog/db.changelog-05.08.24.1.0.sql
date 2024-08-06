--liquibase formatted sql

--changeset roman:1
CREATE TABLE IF NOT EXISTS category
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL UNIQUE
);

--changeset roman:2
CREATE TABLE IF NOT EXISTS news
(
    id                  BIGSERIAL PRIMARY KEY,
    title               VARCHAR(128) NOT NULL UNIQUE,
    text                TEXT         NOT NULL,
    date_of_publication TIMESTAMPTZ  NOT NULL,
    category_id BIGINT REFERENCES category(id) ON DELETE CASCADE NOT NULL
);

