package fr.stoodev.stoodo.post.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class PostContentDTO {
    private UUID id;
    private Long version;
    private String text;
    private PostDTO post;
    private boolean isCurrentVersion;
}
