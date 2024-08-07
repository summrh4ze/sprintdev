package com.example.sprintdev.service;

import com.example.sprintdev.dto.ProjectDTO;
import com.example.sprintdev.dto.SprintDTO;
import com.example.sprintdev.dto.UserDTO;
import com.example.sprintdev.model.*;
import com.example.sprintdev.repository.ProjectRepository;
import com.example.sprintdev.repository.SprintRepository;
import com.example.sprintdev.repository.TicketRepository;
import com.example.sprintdev.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            SprintRepository sprintRepository,
            TicketRepository ticketRepository,
            UserRepository userRepository
    ) {
        this.projectRepository = projectRepository;
        this.sprintRepository = sprintRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Secured({"ROLE_ADMIN"})
    @Transactional
    public List<ProjectDTO> getAllProjects() {
        return this.projectRepository.findAll().stream().map(ProjectDTO::new).toList();
    }

    @Secured({"ROLE_ADMIN"})
    @Transactional
    public ProjectDTO createProject(ProjectDTO project) {
        List<Long> userIds = project.getAssignedUsers().stream().map(UserDTO::getId).toList();
        List<User> assignedUsers = this.userRepository.findAllById(userIds);
        assignedUsers.forEach(u -> {
            UserDTO userdto = project.getAssignedUsers().stream().filter(usr -> usr.getId() == u.getId()).findFirst().orElseThrow();
            if (u.getRoles().contains(UserRole.ADMIN)) {
                List<UserRole> userRoles = new ArrayList<>(userdto.getRoles().stream().map(UserRole::valueOf).toList());
                userRoles.add(UserRole.ADMIN);
                u.setRoles(userRoles);
            } else {
                u.setRoles(new ArrayList<>(userdto.getRoles().stream().map(UserRole::valueOf).toList()));
            }
        });
        List<User> savedAssignedUsers = this.userRepository.saveAll(assignedUsers);
        Project newProject = new Project(project.getName(), project.getDescription());
        Project savedProject = this.projectRepository.save(newProject);
        savedAssignedUsers.forEach(savedProject::assignUser);
        savedProject = this.projectRepository.save(newProject);
        return new ProjectDTO(savedProject);
    }

    @Secured({"ROLE_PO", "ROLE_SM"})
    @Transactional
    public SprintDTO createSprint(User user, SprintDTO sprintData) {
        List<Ticket> tickets = this.ticketRepository.findAllById(sprintData.getTicketIds());
        Sprint sprint = new Sprint(sprintData.getName(), sprintData.getDescription(), sprintData.getEndDate());
        Sprint savedSprint = this.sprintRepository.save(sprint);
        tickets.forEach(ticket -> {
            ticket.setSprint(user, savedSprint);
        });
        this.ticketRepository.saveAll(tickets);
        SprintDTO resp = new SprintDTO(savedSprint);
        resp.setTicketIds(sprintData.getTicketIds());
        return resp;
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    public ProjectDTO updateProject(Long id, ProjectDTO projectChanges) {
        Project project = this.projectRepository.findById(id).orElseThrow();
        project.setName(projectChanges.getName());
        project.setDescription(projectChanges.getDescription());
        projectChanges.getAssignedUsers().forEach(u -> {
            User user = this.userRepository.findById(u.getId()).orElseThrow();
            project.assignUser(user);
        });
        return new ProjectDTO(project);
    }
}
