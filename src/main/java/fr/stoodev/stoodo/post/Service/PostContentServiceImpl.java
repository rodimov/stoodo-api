package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.*;
import fr.stoodev.stoodo.post.Entity.Post;
import fr.stoodev.stoodo.post.Entity.PostContent;
import fr.stoodev.stoodo.post.Repository.PostContentRepository;
import fr.stoodev.stoodo.post.Repository.PostRepository;
import fr.stoodev.stoodo.user.User;
import fr.stoodev.stoodo.user.UserRole;
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
public class PostContentServiceImpl implements PostContentService {
    private final PostContentRepository postContentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Optional<PostContentDTO> create(PostContentCreationDTO postContentCreationDTO) {
        PostContent postContent = this.modelMapper.map(postContentCreationDTO, PostContent.class);
        postContent.setId(null);
        postContent.setCurrentVersion(false);

        Optional<Post> post = postRepository.findById(postContentCreationDTO.getPostId());

        if (post.isEmpty()) {
            return Optional.empty();
        }

        postContent.setPost(post.get());

        var lastContent = this.postContentRepository.findTopByPostOrderByVersionDesc(post.get());

        if (lastContent.isPresent()) {
            postContent.setVersion(lastContent.get().getVersion() + 1);
        } else {
            postContent.setVersion(1L);
        }

        postContent = this.postContentRepository.save(postContent);

        return Optional.of(this.modelMapper.map(postContent, PostContentDTO.class));
    }

    @Override
    public Optional<PostContentDTO> getOneByPostId(UUID postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            return Optional.empty();
        }

        var postContent = postContentRepository.findTopByPostAndIsCurrentVersionOrderByVersionDesc(post.get(),
                true)
                .or(() -> postContentRepository.findTopByPostOrderByVersionDesc(post.get()));

        if (postContent.isEmpty()) {
            return Optional.empty();
        }

        return postContent.map(value -> this.modelMapper.map(value, PostContentDTO.class));
    }

    @Override
    public Page<PostContentDTO> getListByPostId(UUID postId, int page, int size) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            return Page.empty();
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(UserRole.ADMIN.name()))
                || !Objects.equals(user.getId(), post.get().getOwner().getId())) {
            return Page.empty();
        }

        PageRequest pr = PageRequest.of(page, size);
        Page<PostContent> postContents = this.postContentRepository.findByPost(post.get(), pr);

        return postContents.map(postContent -> this.modelMapper.map(postContent, PostContentDTO.class));
    }
}
