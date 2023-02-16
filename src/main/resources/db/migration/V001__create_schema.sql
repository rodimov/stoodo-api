CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(256),
    last_name VARCHAR(256),
    created_by BIGINT,
    created_at TIMESTAMP,
    last_modified_by BIGINT,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(256),
    slug VARCHAR(256),
    description VARCHAR(256),
    owner_id BIGINT REFERENCES users
);
