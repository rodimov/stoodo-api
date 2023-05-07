package fr.stoodev.stoodo.post;

import fr.stoodev.stoodo.post.DTO.PostCreationDTO;
import fr.stoodev.stoodo.post.DTO.PostDTO;
import fr.stoodev.stoodo.post.DTO.TopicCreationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/post")
@Tag(name = "Post", description = "The Post API. Contains all the operations that can be performed on a post.")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
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

    @GetMapping("/get_by_id/{id}")
    @Operation(summary = "Get post", description = "Return post by id")
    public ResponseEntity<PostDTO> getOneById(@PathVariable("id") long postId) {
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

    @GetMapping("/list")
    @Operation(summary = "Get posts list", description = "Return posts list")
    public ResponseEntity<Page<PostDTO>> getList(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.postService.getList(page, size), HttpStatus.OK);
    }

    @GetMapping("/topics_list")
    @Operation(summary = "Get topics list", description = "Return topics list")
    public ResponseEntity<Page<Topic>> getTopicsList(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.topicService.getList(page, size), HttpStatus.OK);
    }
}
