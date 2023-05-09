package fr.stoodev.stoodo.post.DTO;

import fr.stoodev.stoodo.auditable.AuditableDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostContentDTO extends AuditableDTO {
    private UUID id;
    private Long version;
    private String text;
    private PostDTO post;
    private boolean isCurrentVersion;
}
