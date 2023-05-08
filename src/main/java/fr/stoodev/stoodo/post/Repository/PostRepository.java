package fr.stoodev.stoodo.post.Repository;

import fr.stoodev.stoodo.post.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findBySlug(String slug);
    Page<Post> findByIsPublished(boolean isPublished, Pageable pageable);
}
