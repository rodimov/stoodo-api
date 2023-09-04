package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.PostContentCreationDTO;
import fr.stoodev.stoodo.post.DTO.PostContentDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface PostContentService {
    Optional<PostContentDTO> create(PostContentCreationDTO postContentCreationDTO);
    Optional<PostContentDTO> getOneByPostId(UUID postId);
    Page<PostContentDTO> getListByPostId(UUID postId, int page, int size);
    Optional<PostContentDTO> getOneByPostSlug(String slug);
}
