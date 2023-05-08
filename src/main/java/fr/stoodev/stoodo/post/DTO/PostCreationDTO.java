package fr.stoodev.stoodo.post.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreationDTO {
    private String title;
    private String slug;
    private String imageURL;
    private String description;
    private UUID topicId;
    private List<String> tagsList;
}
