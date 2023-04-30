CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(256),
    last_name VARCHAR(256),
    username VARCHAR(256) UNIQUE,
    email VARCHAR(256) UNIQUE,
    password VARCHAR(256),
    role VARCHAR(256),
    is_expired BOOLEAN,
    is_locked BOOLEAN,
    is_credentials_valid BOOLEAN,
    is_active BOOLEAN,
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

CREATE TABLE IF NOT EXISTS refresh_tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(256),
    token_type VARCHAR(256),
    is_revoked BOOLEAN,
    is_expired BOOLEAN,
    user_id BIGINT REFERENCES users
);
