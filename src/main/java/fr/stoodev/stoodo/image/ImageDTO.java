package fr.stoodev.stoodo.image;

import fr.stoodev.stoodo.auditable.AuditableDTO;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO extends AuditableDTO {
    private UUID id;
    private String url;
}
