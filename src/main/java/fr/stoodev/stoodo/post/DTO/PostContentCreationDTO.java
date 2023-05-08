package fr.stoodev.stoodo.post.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class PostContentCreationDTO {
    private String text;
    private UUID postId;
}
