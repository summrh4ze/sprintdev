package com.example.sprintdev.config;

import com.example.sprintdev.dto.UserDTO;
import com.example.sprintdev.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
public class JWTSecurityConfig {

    private final UserService userService;

    public JWTSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       /* List<String> authorizedRoles = Arrays.stream(UserRole.values())
                .filter(role -> role != UserRole.UNASSIGNED)
                .map(role -> "ROLE_" + role)
                .toList();*/
        http.authorizeHttpRequests(authorize -> authorize
                /*.requestMatchers("/tickets/**")
                .hasAnyAuthority(authorizedRoles.toArray(String[]::new))*/
                .anyRequest()
                .authenticated())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwt -> {
                    UserDTO self = this.userService.getSelf(jwt);
                    Map<String, List<String>> realmAccess = jwt.getClaim("realm_access");
                    List<String> roles = realmAccess.get("roles");
                    List<SimpleGrantedAuthority> grantedAuthorities = roles
                            .stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .toList();
                    List<SimpleGrantedAuthority> grantedAuthoritiesWithCustomRole = new ArrayList<>(grantedAuthorities);
                    self.getRoles().forEach(role -> {
                        grantedAuthoritiesWithCustomRole.add(new SimpleGrantedAuthority("ROLE_" + role));
                    });
                    return new JwtAuthenticationToken(jwt, grantedAuthoritiesWithCustomRole);
                })));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
