package com.example.sprintdev.service;

import com.example.sprintdev.dto.UserDTO;
import com.example.sprintdev.model.User;
import com.example.sprintdev.model.UserRole;
import com.example.sprintdev.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserService {
    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getSelf() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        return getSelf(jwt);
    }

    public synchronized UserDTO getSelf(Jwt jwt) {
        // inset self in DB if not already there
        User self = this.userRepository.findByEmail(jwt.getClaim("email"));
        if (self == null) {
            List<UserRole> currentRoles = new ArrayList<>();
            currentRoles.add(UserRole.UNASSIGNED);
            Map<String, List<String>> realmAccess = jwt.getClaim("realm_access");
            List<String> roles = realmAccess.get("roles");
            if (roles.contains("ADMIN")) {
                currentRoles.add(UserRole.ADMIN);
            }
            self = new User(
                    currentRoles,
                    jwt.getClaim("given_name"),
                    jwt.getClaim("family_name"),
                    jwt.getClaim("preferred_username"),
                    jwt.getClaim("email")
            );
            self = this.userRepository.save(self);
        }
        return new UserDTO(self);
    }

    public User getUserSelf() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        return this.userRepository.findByEmail(jwt.getClaim("email"));
    };

    @Secured({"ROLE_ADMIN"})
    public List<UserDTO> getAll() {
        return this.userRepository.findAll().stream().map(UserDTO::new).toList();
    }
}
