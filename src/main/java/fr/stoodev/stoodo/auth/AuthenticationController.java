package fr.stoodev.stoodo.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "The Authentication API")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void register(
            @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.register(request, response);
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
