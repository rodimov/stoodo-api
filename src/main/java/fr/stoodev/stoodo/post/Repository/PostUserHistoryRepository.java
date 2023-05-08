package fr.stoodev.stoodo.post.Repository;

import fr.stoodev.stoodo.post.Entity.PostUserHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostUserHistoryRepository extends JpaRepository<PostUserHistory, UUID> {
    Optional<PostUserHistory> findByPostIdAndUserId(UUID postId, UUID userId);
    Page<PostUserHistory> findByUserId(UUID userId, Pageable pageable);
    long countByPostIdAndIsOpened(UUID postId, boolean is_opened);
    long countByPostIdAndIsViewed(UUID postId, boolean is_viewed);
    long countByPostIdAndIsLiked(UUID postId, boolean is_liked);
}
