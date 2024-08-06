DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS category;

CREATE TABLE category
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE news
(
    id                  BIGSERIAL PRIMARY KEY,
    title               VARCHAR(128) NOT NULL UNIQUE,
    text                TEXT         NOT NULL,
    date_of_publication TIMESTAMPTZ  NOT NULL,
    category_id BIGINT REFERENCES category(id) not null
);