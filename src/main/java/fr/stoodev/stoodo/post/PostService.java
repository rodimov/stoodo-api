package fr.stoodev.stoodo.post;

import fr.stoodev.stoodo.post.DTO.PostCreationDTO;
import fr.stoodev.stoodo.post.DTO.PostDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostService {
    PostDTO create(PostCreationDTO postCreationDTO);
    Optional<PostDTO> getOneById(long postId);
    Optional<PostDTO> getOneBySlug(String slug);
    Page<PostDTO> getList(int page, int size);
}
