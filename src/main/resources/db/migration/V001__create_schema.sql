CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL,
    username VARCHAR(256) UNIQUE NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL,
    role VARCHAR(256) NOT NULL,
    is_expired BOOLEAN NOT NULL,
    is_locked BOOLEAN NOT NULL,
    is_credentials_valid BOOLEAN NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_by BIGINT REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by BIGINT REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(256) UNIQUE NOT NULL,
    slug VARCHAR(256) UNIQUE NOT NULL,
    image_url VARCHAR(1024),
    description VARCHAR(1024),
    owner_id BIGINT REFERENCES users,
    is_published BOOLEAN NOT NULL,
    created_by BIGINT REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by BIGINT REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS posts_content (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    post_id BIGINT REFERENCES posts NOT NULL,
    is_current_version BOOLEAN NOT NULL,
    previous_version BIGINT REFERENCES posts_content,
    next_version BIGINT REFERENCES posts_content,
    created_by BIGINT REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by BIGINT REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    tag VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS posts_tags (
    id SERIAL PRIMARY KEY,
    tags_id BIGINT REFERENCES tags,
    post_id BIGINT REFERENCES posts
);

CREATE TABLE IF NOT EXISTS topics (
    id SERIAL PRIMARY KEY,
    topic VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS posts_topics (
    id SERIAL PRIMARY KEY,
    topics_id BIGINT REFERENCES topics,
    post_id BIGINT REFERENCES posts
);

CREATE TABLE IF NOT EXISTS refresh_tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(256) NOT NULL,
    token_type VARCHAR(256) NOT NULL,
    is_revoked BOOLEAN NOT NULL,
    is_expired BOOLEAN NOT NULL,
    user_id BIGINT REFERENCES users
);
