package fr.stoodev.stoodo.security;

import fr.stoodev.stoodo.security.jwt.JWTAuthenticationFilter;
import fr.stoodev.stoodo.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/user/get/**").permitAll()
                .requestMatchers("/user/list").hasAuthority(UserRole.ADMIN.name())
                .requestMatchers("/user/create").hasAuthority(UserRole.ADMIN.name())
                .requestMatchers("/post/get_by_id/**").permitAll()
                .requestMatchers("/post/get_by_slug/**").permitAll()
                .requestMatchers("/post/list_published").permitAll()
                .requestMatchers("/post/list_not_published").hasAnyAuthority(UserRole.ADMIN.name())
                .requestMatchers("/post/list_all").hasAnyAuthority(UserRole.ADMIN.name())
                .requestMatchers("/post/create").hasAnyAuthority(UserRole.ADMIN.name(),
                        UserRole.CONTRIBUTOR.name(), UserRole.SUPPORT.name())
                .requestMatchers("/post/create_topic").hasAuthority(UserRole.ADMIN.name())
                .requestMatchers("/post/create_post_content").hasAnyAuthority(UserRole.ADMIN.name(),
                        UserRole.CONTRIBUTOR.name(), UserRole.SUPPORT.name())
                .requestMatchers("/post/topics_list").permitAll()
                .requestMatchers("/post/get_content_by_post_id/**").permitAll()
                .requestMatchers("/post/post_stat/**").permitAll()
                .requestMatchers("/image/upload").hasAnyAuthority(UserRole.ADMIN.name(),
                        UserRole.CONTRIBUTOR.name(), UserRole.SUPPORT.name())
                .requestMatchers("/image/get_by_id/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
