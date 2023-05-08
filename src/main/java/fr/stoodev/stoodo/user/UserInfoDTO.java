package fr.stoodev.stoodo.user;

import fr.stoodev.stoodo.auditable.AuditableDTO;
import lombok.*;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO extends AuditableDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
