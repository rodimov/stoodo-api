package fr.stoodev.stoodo.post.Repository;

import fr.stoodev.stoodo.post.Entity.Post;
import fr.stoodev.stoodo.post.Entity.PostContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostContentRepository extends JpaRepository<PostContent, Long> {
    Optional<PostContent> findTopByPostOrderByIdDesc(Post post);
    Optional<PostContent> findTopByPostAndIsCurrentVersionOrderByIdDesc(Post post, boolean isCurrentVersion);
    Page<PostContent> findByPost(Post post, Pageable pageable);
}
