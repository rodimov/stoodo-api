package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.PostDTO;
import fr.stoodev.stoodo.post.DTO.PostUserHistoryDTO;
import fr.stoodev.stoodo.post.DTO.PostUserInteractionDTO;
import fr.stoodev.stoodo.post.Entity.Post;
import fr.stoodev.stoodo.post.Entity.PostUserHistory;
import fr.stoodev.stoodo.post.Repository.PostUserHistoryRepository;
import fr.stoodev.stoodo.user.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostUserHistoryServiceImpl implements PostUserHistoryService {
    private final PostUserHistoryRepository postUserHistoryRepository;
    private final PostService postService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    private Optional<PostUserHistory> getOrCreatePostUserHistory(UUID postId, UUID userId) {
        Optional<PostUserHistory> postUserHistory = postUserHistoryRepository.findByPostIdAndUserId(postId, userId);

        if (postUserHistory.isEmpty()) {
            Optional<PostDTO> post = postService.getOneById(postId);
            Optional<UserInfoDTO> user = userService.getOne(userId);

            if (post.isEmpty() || user.isEmpty()) {
                return Optional.empty();
            }

            postUserHistory = Optional.of(
                    postUserHistoryRepository.save(
                            PostUserHistory.builder()
                                    .post(modelMapper.map(post, Post.class))
                                    .user(modelMapper.map(user, User.class))
                                    .isOpened(false)
                                    .isViewed(false)
                                    .isLiked(false)
                                    .build()
                    )
            );
        }

        return postUserHistory;
    }

    private UUID getAuthenticatedUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    @Override
    @Transactional
    public Optional<PostUserInteractionDTO> getPostUserInteraction(UUID postId) {
        return this.getOrCreatePostUserHistory(postId, getAuthenticatedUser())
                .map(value -> this.modelMapper.map(value, PostUserInteractionDTO.class));
    }

    @Override
    @Transactional
    public Optional<PostUserInteractionDTO> setLiked(UUID postId, boolean isLiked) {
        Optional<PostUserHistory> postUserHistory = this.getOrCreatePostUserHistory(postId, getAuthenticatedUser());

        if (postUserHistory.isEmpty()) {
            return Optional.empty();
        }

        postUserHistory = postUserHistory.map(value -> {
            value.setLiked(isLiked);
            return value;
        });

        PostUserHistory history = postUserHistoryRepository.save(postUserHistory.get());
        return Optional.of(this.modelMapper.map(history, PostUserInteractionDTO.class));
    }

    @Override
    @Transactional
    public Optional<PostUserInteractionDTO> setOpened(UUID postId) {
        Optional<PostUserHistory> postUserHistory = this.getOrCreatePostUserHistory(postId, getAuthenticatedUser());

        if (postUserHistory.isEmpty()) {
            return Optional.empty();
        }

        postUserHistory = postUserHistory.map(value -> {
            value.setOpened(true);
            return value;
        });

        PostUserHistory history = postUserHistoryRepository.save(postUserHistory.get());
        return Optional.of(this.modelMapper.map(history, PostUserInteractionDTO.class));
    }

    @Override
    @Transactional
    public Optional<PostUserInteractionDTO> setViewed(UUID postId) {
        Optional<PostUserHistory> postUserHistory = this.getOrCreatePostUserHistory(postId, getAuthenticatedUser());

        if (postUserHistory.isEmpty()) {
            return Optional.empty();
        }

        postUserHistory = postUserHistory.map(value -> {
            value.setViewed(true);
            return value;
        });

        PostUserHistory history = postUserHistoryRepository.save(postUserHistory.get());
        return Optional.of(this.modelMapper.map(history, PostUserInteractionDTO.class));
    }

    @Override
    public Optional<Long> countPostLikes(UUID postId) {
        Optional<PostDTO> post = postService.getOneById(postId);

        if (post.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(postUserHistoryRepository.countByPostIdAndIsLiked(postId, true));
    }

    @Override
    public Optional<Long> countPostViews(UUID postId) {
        Optional<PostDTO> post = postService.getOneById(postId);

        if (post.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(postUserHistoryRepository.countByPostIdAndIsViewed(postId, true));
    }

    @Override
    public Optional<Long> countPostOpened(UUID postId) {
        Optional<PostDTO> post = postService.getOneById(postId);

        if (post.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(postUserHistoryRepository.countByPostIdAndIsOpened(postId, true));
    }

    @Override
    public Page<PostUserHistoryDTO> getUserHistory(UUID userId, int page, int size) {
        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userAuth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(UserRole.ADMIN.name()))
                && userAuth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(UserRole.SUPPORT.name()))
                || !Objects.equals(userAuth.getId(), userId)) {
            return Page.empty();
        }

        PageRequest pr = PageRequest.of(page, size);
        Page<PostUserHistory> userHistory = postUserHistoryRepository.findByUserId(userId, pr);
        return userHistory.map(history -> this.modelMapper.map(history, PostUserHistoryDTO.class));
    }
}
