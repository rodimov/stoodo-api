package fr.stoodev.stoodo.post.Controller;

import fr.stoodev.stoodo.post.DTO.*;
import fr.stoodev.stoodo.post.Entity.Topic;
import fr.stoodev.stoodo.post.Service.PostContentService;
import fr.stoodev.stoodo.post.Service.PostService;
import fr.stoodev.stoodo.post.Service.PostUserHistoryService;
import fr.stoodev.stoodo.post.Service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@Tag(name = "Post", description = "The Post API. Contains all the operations that can be performed on a post.")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostContentService postContentService;
    private final PostUserHistoryService postUserHistoryService;
    private final TopicService topicService;

    @PostMapping("/create")
    @Operation(summary = "Create post", description = "Create post")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostDTO> create(@RequestBody PostCreationDTO post) {
        return new ResponseEntity<>(this.postService.create(post), HttpStatus.CREATED);
    }

    @PostMapping("/create_topic")
    @Operation(summary = "Create topic", description = "Create topic")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Topic> create(@RequestBody TopicCreationDTO topic) {
        return new ResponseEntity<>(this.topicService.create(topic), HttpStatus.CREATED);
    }

    @PostMapping("/create_post_content")
    @Operation(summary = "Create post content", description = "Create post content")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostContentDTO> create(@RequestBody PostContentCreationDTO postContentCreationDTO) {
        Optional<PostContentDTO> postContentDTOOptional = this.postContentService.create(postContentCreationDTO);

        return postContentDTOOptional.map(postContentDTO -> new ResponseEntity<>(postContentDTO, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));

    }

    @GetMapping("/get_by_id/{id}")
    @Operation(summary = "Get post", description = "Return post by id")
    public ResponseEntity<PostDTO> getOneById(@PathVariable("id") UUID postId) {
        Optional<PostDTO> post = this.postService.getOneById(postId);

        return post.map(postDTO -> new ResponseEntity<>(postDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/get_by_slug/{slug}")
    @Operation(summary = "Get post", description = "Return post by slug")
    public ResponseEntity<PostDTO> getOneBySlug(@PathVariable("slug") String slug) {
        Optional<PostDTO> post = this.postService.getOneBySlug(slug);

        return post.map(postDTO -> new ResponseEntity<>(postDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/get_content_by_post_id/{id}")
    @Operation(summary = "Get post content by post id", description = "Return post content by post id")
    public ResponseEntity<PostContentDTO> getOnePostContentByPostId(@PathVariable("id") UUID postId) {
        Optional<PostContentDTO> postContent = this.postContentService.getOneByPostId(postId);

        return postContent.map(postContentDTO -> new ResponseEntity<>(postContentDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/list_published")
    @Operation(summary = "Get published posts list", description = "Return published posts list")
    public ResponseEntity<Page<PostDTO>> getListPublished(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.postService.getListPublished(page, size), HttpStatus.OK);
    }

    @GetMapping("/list_not_published")
    @Operation(summary = "Get not published posts list", description = "Return not published posts list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<PostDTO>> getListNotPublished(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.postService.getListNotPublished(page, size), HttpStatus.OK);
    }

    @GetMapping("/list_all")
    @Operation(summary = "Get all posts list", description = "Return all posts list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<PostDTO>> getListAll(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.postService.getListAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/topics_list")
    @Operation(summary = "Get topics list", description = "Return topics list")
    public ResponseEntity<Page<Topic>> getTopicsList(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.topicService.getList(page, size), HttpStatus.OK);
    }

    @GetMapping("/list_content_by_post_id/{id}")
    @Operation(summary = "Get post content by post id", description = "Return post content by post id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<PostContentDTO>> getPostContentListByPostId(@PathVariable("id") UUID postId,
                                                                           @RequestParam int page,
                                                                           @RequestParam int size) {
        return new ResponseEntity<>(this.postContentService.getListByPostId(postId, page, size), HttpStatus.OK);
    }

    @GetMapping("/post_stat/{id}")
    @Operation(summary = "Get post likes, opened and views count",
            description = "Return post likes, opened and views count by post id")
    public ResponseEntity<PostStatisticsDTO> getPostLikeCount(@PathVariable("id") UUID postId) {
        Optional<Long> postLikesCount = postUserHistoryService.countPostLikes(postId);
        Optional<Long> postOpenedCount = postUserHistoryService.countPostOpened(postId);
        Optional<Long> postViewsCount = postUserHistoryService.countPostViews(postId);

        if (postLikesCount.isEmpty() || postOpenedCount.isEmpty() || postViewsCount.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                PostStatisticsDTO.builder()
                        .likesCount(postLikesCount.get())
                        .openedCount(postOpenedCount.get())
                        .viewsCount(postViewsCount.get())
                        .build(),
                HttpStatus.OK);
    }

    @PostMapping("/like_post/{id}")
    @Operation(summary = "Like post", description = "Like post by post id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostUserHistoryDTO> setLikedPost(@PathVariable("id") UUID postId,
                                                           @RequestParam boolean isLiked) {
        Optional<PostUserHistoryDTO> postUserHistoryDTO = postUserHistoryService.setLiked(postId, isLiked);

        return postUserHistoryDTO.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/open_post/{id}")
    @Operation(summary = "Open post", description = "Open post by post id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostUserHistoryDTO> setOpenedPost(@PathVariable("id") UUID postId) {
        Optional<PostUserHistoryDTO> postUserHistoryDTO = postUserHistoryService.setOpened(postId);

        return postUserHistoryDTO.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/view_post/{id}")
    @Operation(summary = "View post", description = "View post by post id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostUserHistoryDTO> setViewPost(@PathVariable("id") UUID postId) {
        Optional<PostUserHistoryDTO> postUserHistoryDTO = postUserHistoryService.setViewed(postId);

        return postUserHistoryDTO.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user_history/{id}")
    @Operation(summary = "Get user history", description = "Return user history by user id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<PostUserHistoryDTO>> getUserHistory(@PathVariable("id") UUID userId,
                                                                   @RequestParam int page,
                                                                   @RequestParam int size) {
        return new ResponseEntity<>(postUserHistoryService.getUserHistory(userId, page, size), HttpStatus.OK);
    }
}
