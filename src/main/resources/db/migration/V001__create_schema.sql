CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL,
    username VARCHAR(256) UNIQUE NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL,
    role VARCHAR(256),
    is_expired BOOLEAN,
    is_locked BOOLEAN,
    is_credentials_valid BOOLEAN,
    is_active BOOLEAN,
    created_by BIGINT REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by BIGINT REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(256),
    slug VARCHAR(256) UNIQUE NOT NULL,
    image_url VARCHAR(1024),
    description VARCHAR(1024),
    owner_id BIGINT REFERENCES users,
    is_published BOOLEAN,
    created_by BIGINT REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by BIGINT REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS posts_content (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    post_id BIGINT REFERENCES posts,
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

CREATE TABLE IF NOT EXISTS tags_posts (
    id SERIAL PRIMARY KEY,
    tag_id BIGINT REFERENCES tags,
    post_id BIGINT REFERENCES posts
);

CREATE TABLE IF NOT EXISTS topics (
    id SERIAL PRIMARY KEY,
    topic VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS topics_posts (
    id SERIAL PRIMARY KEY,
    topic_id BIGINT REFERENCES topics,
    post_id BIGINT REFERENCES posts
);

CREATE TABLE IF NOT EXISTS refresh_tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(256),
    token_type VARCHAR(256),
    is_revoked BOOLEAN,
    is_expired BOOLEAN,
    user_id BIGINT REFERENCES users
);
