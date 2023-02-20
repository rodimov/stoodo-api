package fr.stoodev.stoodo.user;

import fr.stoodev.stoodo.auditable.AuditableDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO extends AuditableDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
