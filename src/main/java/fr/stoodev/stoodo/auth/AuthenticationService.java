package fr.stoodev.stoodo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.stoodev.stoodo.post.DTO.PostDTO;
import fr.stoodev.stoodo.security.jwt.JWTService;
import fr.stoodev.stoodo.token.Token;
import fr.stoodev.stoodo.token.TokenRepository;
import fr.stoodev.stoodo.token.TokenType;
import fr.stoodev.stoodo.user.UserInfoDTO;
import fr.stoodev.stoodo.user.UserRole;
import fr.stoodev.stoodo.user.User;
import fr.stoodev.stoodo.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    @Value("${stoodo.security.secured-cookies}")
    private boolean isSecuredCookies;


    public UserInfoDTO register(RegisterRequest request) {
        User user = this.modelMapper.map(request, User.class);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        user = this.userRepository.save(user);

        return this.modelMapper.map(user, UserInfoDTO.class);
    }

    public void authenticate(AuthenticationRequest request,
                             HttpServletResponse response
    ) throws IOException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        request.getPassword()
                )
        );

        var accessToken = jwtService.generateAccessToken(user);

        if (request.isSaveSession()) {
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user, refreshToken);
            saveRefreshTokenInCookies(response, refreshToken);
        }

        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();

        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        response.setStatus(200);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        var refreshTokenCookie = getRefreshTokenInCookies(request);

        final String username;

        if (refreshTokenCookie.isEmpty()) {
            return;
        }

        String refreshToken = refreshTokenCookie.get();
        username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            return;
        }

        var user = this.userRepository.findByUsername(username)
                .orElseThrow();

        if (!isRefreshTokenValid(refreshToken, user)) {
            removeRefreshTokenInCookies(response);
            return;
        }

        var accessToken = jwtService.generateAccessToken(user);

        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();

        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        response.setStatus(200);

    }

    public Optional<UserInfoDTO> getAuthUserInfo() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return Optional.empty();
        }

        var principal = auth.getPrincipal();

        if (principal instanceof User user) {
            return Optional.of(this.modelMapper.map(user, UserInfoDTO.class));
        }

        return Optional.empty();
    }

    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var refreshTokenCookie = getRefreshTokenInCookies(request);

        final String username;

        if (refreshTokenCookie.isEmpty()) {
            return;
        }

        String refreshToken = refreshTokenCookie.get();
        username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            return;
        }

        var user = this.userRepository.findByUsername(username)
                .orElseThrow();

        if (!isRefreshTokenValid(refreshToken, user)) {
            removeRefreshTokenInCookies(response);
            return;
        }

        revokeToken(refreshToken);
        removeRefreshTokenInCookies(response);
        response.setStatus(200);

    }

    public void logoutAll(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var refreshTokenCookie = getRefreshTokenInCookies(request);

        final String username;

        if (refreshTokenCookie.isEmpty()) {
            return;
        }

        String refreshToken = refreshTokenCookie.get();
        username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            return;
        }

        var user = this.userRepository.findByUsername(username)
                .orElseThrow();

        if (!isRefreshTokenValid(refreshToken, user)) {
            removeRefreshTokenInCookies(response);
            return;
        }

        revokeAllUserTokens(user);
        removeRefreshTokenInCookies(response);
        response.setStatus(200);

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeToken(String token) {
        var refreshToken = tokenRepository.findByToken(token);

        if (refreshToken.isPresent()) {
            refreshToken.get().setRevoked(true);
            tokenRepository.save(refreshToken.get());
        }
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private boolean isRefreshTokenValid(String refreshToken, User user) {
        var token = tokenRepository.findByToken(refreshToken);

        return token.isPresent() && jwtService.isTokenValid(refreshToken, user) &&
                !token.get().isRevoked() && !token.get().isExpired();
    }

    private void saveRefreshTokenInCookies(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE, refreshToken);
        cookie.setPath("/");
        cookie.setMaxAge(2592000);
        cookie.setSecure(isSecuredCookies);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private void removeRefreshTokenInCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setSecure(isSecuredCookies);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private Optional<String> getRefreshTokenInCookies(HttpServletRequest request) {
        var cookies = request.getCookies();

        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_COOKIE))
                .map(Cookie::getValue)
                .findAny();
    }

}
