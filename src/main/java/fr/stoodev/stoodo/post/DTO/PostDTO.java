package fr.stoodev.stoodo.post.DTO;

import fr.stoodev.stoodo.auditable.AuditableDTO;
import fr.stoodev.stoodo.post.Entity.Tag;
import fr.stoodev.stoodo.post.Entity.Topic;
import fr.stoodev.stoodo.user.UserInfoDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO extends AuditableDTO {
    private UUID id;
    private String title;
    private String slug;
    private String imageURL;
    private String description;
    private Topic topic;
    private Boolean isPublished;
    private UserInfoDTO owner;
    private List<Tag> tags;
}
