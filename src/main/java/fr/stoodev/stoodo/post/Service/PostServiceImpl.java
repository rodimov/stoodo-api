package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.PostCreationDTO;
import fr.stoodev.stoodo.post.DTO.PostDTO;
import fr.stoodev.stoodo.post.DTO.TagCreationDTO;
import fr.stoodev.stoodo.post.Entity.Post;
import fr.stoodev.stoodo.post.Repository.PostRepository;
import fr.stoodev.stoodo.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagService tagService;
    private final TopicService topicService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDTO create(PostCreationDTO postCreationDTO) {
        Post post = this.modelMapper.map(postCreationDTO, Post.class);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        post.setOwner(user);
        post.setIsPublished(false);

        var tags = postCreationDTO.getTagsList()
                .stream()
                .map(tag -> tagService.create(new TagCreationDTO(tag)))
                .toList();

        post.setTags(tags);

        var topics = postCreationDTO.getTopicsList()
                .stream()
                .map(topicService::getOneById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        post.setTopics(topics);

        post = this.postRepository.save(post);
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public Optional<PostDTO> getOneById(long postId) {
        Optional<Post> post = this.postRepository.findById(postId);
        return post.map(value -> this.modelMapper.map(value, PostDTO.class));
    }

    @Override
    public Optional<PostDTO> getOneBySlug(String slug) {
        Optional<Post> post = this.postRepository.findBySlug(slug);
        return post.map(value -> this.modelMapper.map(value, PostDTO.class));
    }

    @Override
    public Page<PostDTO> getListPublished(int page, int size) {
        PageRequest pr = PageRequest.of(page, size, Sort.by("id"));
        Page<Post> postsPage = this.postRepository.findByIsPublished(true, pr);
        return postsPage.map(post -> this.modelMapper.map(post, PostDTO.class));
    }

    @Override
    public Page<PostDTO> getListNotPublished(int page, int size) {
        PageRequest pr = PageRequest.of(page, size, Sort.by("id"));
        Page<Post> postsPage = this.postRepository.findByIsPublished(false, pr);
        return postsPage.map(post -> this.modelMapper.map(post, PostDTO.class));
    }

    @Override
    public Page<PostDTO> getListAll(int page, int size) {
        PageRequest pr = PageRequest.of(page, size, Sort.by("id"));
        Page<Post> postsPage = this.postRepository.findAll(pr);
        return postsPage.map(post -> this.modelMapper.map(post, PostDTO.class));
    }
}
