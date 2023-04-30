package fr.stoodev.stoodo.user;

import fr.stoodev.stoodo.auditable.AuditableDTO;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO extends AuditableDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
