package fr.stoodev.stoodo.post.Repository;

import fr.stoodev.stoodo.post.Entity.Post;
import fr.stoodev.stoodo.post.Entity.PostContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostContentRepository extends JpaRepository<PostContent, UUID> {
    Optional<PostContent> findTopByPostOrderByVersionDesc(Post post);
    Optional<PostContent> findTopByPostAndIsCurrentVersionOrderByVersionDesc(Post post, boolean isCurrentVersion);
    Page<PostContent> findByPost(Post post, Pageable pageable);
}
