package fr.stoodev.stoodo.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
    Optional<Post> findBySlug(String slug);
}
