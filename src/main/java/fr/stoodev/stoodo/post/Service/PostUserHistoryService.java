package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.PostUserHistoryDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface PostUserHistoryService {
    Optional<PostUserHistoryDTO> getPostUserHistory(UUID postId, UUID userId);
    Optional<PostUserHistoryDTO> setLiked(UUID postId, boolean isLiked);
    Optional<PostUserHistoryDTO> setOpened(UUID postId);
    Optional<PostUserHistoryDTO> setViewed(UUID postId);
    Optional<Long> countPostLikes(UUID postId);
    Optional<Long> countPostOpened(UUID postId);
    Optional<Long> countPostViews(UUID postId);
    Page<PostUserHistoryDTO> getUserHistory(UUID userId, int page, int size);
}
