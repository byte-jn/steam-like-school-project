CREATE TABLE IF NOT EXISTS games (
    id          VARCHAR(255)     NOT NULL,
    titel       VARCHAR(255),
    description TEXT,
    price       DOUBLE PRECISION NOT NULL DEFAULT 0,
    release_date TIMESTAMP,
    created_at  TIMESTAMP        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dlcs (
    id           VARCHAR(255)     NOT NULL,
    dlc_name     VARCHAR(255),
    game_title   VARCHAR(255),
    description  TEXT,
    price        DOUBLE PRECISION NOT NULL DEFAULT 0,
    release_date TIMESTAMP,
    created_at   TIMESTAMP        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id        BIGSERIAL    NOT NULL,
    username  VARCHAR(255),
    email     VARCHAR(255),
    password  VARCHAR(255),
    firstname VARCHAR(255),
    lastname  VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_owned_games (
    user_id BIGINT       NOT NULL,
    game_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_owned_dlcs (
    user_id BIGINT       NOT NULL,
    dlc_id  VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
