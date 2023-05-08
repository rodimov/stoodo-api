package fr.stoodev.stoodo.post.DTO;

import lombok.Data;

@Data
public class PostContentCreationDTO {
    private String text;
    private Long postId;
}
