package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.PostCreationDTO;
import fr.stoodev.stoodo.post.DTO.PostDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface PostService {
    PostDTO create(PostCreationDTO postCreationDTO);
    Optional<PostDTO> setPublished(UUID postId, boolean isPublished);
    Optional<PostDTO> getOneById(UUID postId);
    Optional<PostDTO> getOneBySlug(String slug);
    Page<PostDTO> getListPublished(int page, int size);
    Page<PostDTO> getListNotPublished(int page, int size);
    Page<PostDTO> getListAll(int page, int size);
}
