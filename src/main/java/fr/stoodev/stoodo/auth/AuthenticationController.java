package fr.stoodev.stoodo.auth;

import fr.stoodev.stoodo.user.UserInfoDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "The Authentication API")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserInfoDTO> register(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public void authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.authenticate(request, response);
    }

    @PostMapping("/refresh_token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/user_info")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserInfoDTO> getAuthUserInfo() {
        Optional<UserInfoDTO> userInfoDTOOptional = authenticationService.getAuthUserInfo();

        return userInfoDTOOptional.map(userInfo -> new ResponseEntity<>(userInfo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authenticationService.logout(request, response);
    }

    @PostMapping("/logout_all")
    public void logoutAll(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authenticationService.logoutAll(request, response);
    }
}
