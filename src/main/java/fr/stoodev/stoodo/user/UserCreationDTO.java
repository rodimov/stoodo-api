package fr.stoodev.stoodo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
