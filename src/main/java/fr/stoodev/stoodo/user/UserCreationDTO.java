package fr.stoodev.stoodo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationDTO {
    @Pattern(message = "Please enter a valid first name", regexp="^\\w+$")
    @NotBlank(message = "Please enter first name")
    @NotNull(message = "Please enter first name")
    private String firstName;

    @Pattern(message = "Please enter a valid last name", regexp="^\\w+$")
    @NotBlank(message = "Please enter last name")
    @NotNull(message = "Please enter last name")
    private String lastName;

    @Email(message = "Please enter valid email",
            regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
    @NotBlank(message = "Please enter email")
    @NotNull(message = "Please enter email")
    private String email;

    @Pattern(message = "Please enter valid username", regexp = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")
    @NotBlank(message = "Please enter username")
    @NotNull(message = "Please enter username")
    private String username;

    @NotBlank(message = "Please enter password")
    @NotNull(message = "Please enter password")
    private String password;
}
