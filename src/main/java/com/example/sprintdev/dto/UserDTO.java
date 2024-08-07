package com.example.sprintdev.dto;

import com.example.sprintdev.model.User;

import java.util.List;

public class UserDTO {
    private long id;
    private List<String> roles;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Long assignedProjectId;

    public UserDTO() {}

    public UserDTO(User user) {
        this.id = user.getId();
        this.roles = user.getRoles().stream().map(Enum::toString).toList();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        if (user.getAssignedProject() != null) {
            this.assignedProjectId = user.getAssignedProject().getId();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAssignedProjectId() {
        return assignedProjectId;
    }

    public void setAssignedProjectId(Long assignedProjectId) {
        this.assignedProjectId = assignedProjectId;
    }
}
