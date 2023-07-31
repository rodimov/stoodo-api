package fr.stoodev.stoodo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Pattern(message = "Please enter valid first name", regexp = "^\\w+$")
    @NotBlank(message = "Please enter first name")
    @NotNull(message = "Please enter first name")
    private String firstName;

    @Pattern(message = "Please enter valid last name", regexp = "^\\w+$")
    @NotBlank(message = "Please enter last name")
    @NotNull(message = "Please enter last name")
    private String lastName;

    @Pattern(message = "Please enter valid username", regexp = "^(?=[a-zA-Z0-9._]{3,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")
    @NotBlank(message = "Please enter username")
    @NotNull(message = "Please enter username")
    private String username;

    @Email(message = "Please enter valid email",
            regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
    @NotBlank(message = "Please enter email")
    @NotNull(message = "Please enter email")
    private String email;

    @NotBlank(message = "Please enter password")
    @NotNull(message = "Please enter password")
    private String password;
}
