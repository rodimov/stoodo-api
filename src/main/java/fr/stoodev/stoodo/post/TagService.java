package fr.stoodev.stoodo.post;

import fr.stoodev.stoodo.post.DTO.TagCreationDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TagService {
    Tag create(TagCreationDTO tagCreationDTO);
    Optional<Tag> getOneByTag(String tag);
    Page<Tag> getList(int page, int size);
}
