CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
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
    created_by UUID REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by UUID REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS images (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    url TEXT,
    created_by UUID REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by UUID REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS refresh_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    token VARCHAR(256) NOT NULL UNIQUE,
    token_type VARCHAR(256) NOT NULL,
    is_revoked BOOLEAN NOT NULL,
    is_expired BOOLEAN NOT NULL,
    user_id UUID REFERENCES users NOT NULL
);

CREATE TABLE IF NOT EXISTS topics (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    topic VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS tags (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    tag VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS posts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    title VARCHAR(256) UNIQUE NOT NULL,
    slug VARCHAR(256) UNIQUE NOT NULL,
    topic_id UUID REFERENCES topics,
    image_id UUID REFERENCES images,
    description VARCHAR(1024),
    owner_id UUID REFERENCES users,
    is_published BOOLEAN NOT NULL,
    created_by UUID REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by UUID REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS posts_content (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    version BIGINT NOT NULL,
    text TEXT NOT NULL,
    post_id UUID REFERENCES posts NOT NULL,
    is_current_version BOOLEAN NOT NULL,
    created_by UUID REFERENCES users,
    created_at TIMESTAMP,
    last_modified_by UUID REFERENCES users,
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS posts_tags (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    tags_id UUID REFERENCES tags,
    post_id UUID REFERENCES posts
);

CREATE TABLE IF NOT EXISTS posts_user_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    post_id UUID REFERENCES posts NOT NULL,
    user_id UUID REFERENCES users NOT NULL,
    is_opened BOOLEAN NOT NULL,
    is_viewed BOOLEAN NOT NULL,
    is_liked BOOLEAN NOT NULL,
    UNIQUE (post_id, user_id)
);
