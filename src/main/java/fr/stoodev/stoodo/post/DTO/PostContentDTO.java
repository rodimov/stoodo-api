package fr.stoodev.stoodo.post.DTO;

import lombok.Data;

@Data
public class PostContentDTO {
    private Long id;
    private String text;
    private PostDTO post;
    private boolean isCurrentVersion;
}
