package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.PostUserHistoryDTO;
import fr.stoodev.stoodo.post.DTO.PostUserInteractionDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface PostUserHistoryService {
    Optional<PostUserInteractionDTO> getPostUserInteraction(UUID postId);
    Optional<PostUserInteractionDTO> setLiked(UUID postId, boolean isLiked);
    Optional<PostUserInteractionDTO> setOpened(UUID postId);
    Optional<PostUserInteractionDTO> setViewed(UUID postId);
    Optional<Long> countPostLikes(UUID postId);
    Optional<Long> countPostOpened(UUID postId);
    Optional<Long> countPostViews(UUID postId);
    Page<PostUserHistoryDTO> getUserHistory(UUID userId, int page, int size);
}
