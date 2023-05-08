package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.PostContentCreationDTO;
import fr.stoodev.stoodo.post.DTO.PostContentDTO;
import fr.stoodev.stoodo.post.DTO.PostDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostContentService {
    Optional<PostContentDTO> create(PostContentCreationDTO postContentCreationDTO);
    Optional<PostContentDTO> getOneByPostId(Long postId);
    Page<PostContentDTO> getListByPostId(Long postId, int page, int size);
}
