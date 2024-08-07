package com.example.sprintdev.controller;

import com.example.sprintdev.dto.ProjectDTO;
import com.example.sprintdev.dto.SprintDTO;
import com.example.sprintdev.model.User;
import com.example.sprintdev.service.ProjectService;
import com.example.sprintdev.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<ProjectDTO> getAllProjects() {
        return this.projectService.getAllProjects();
    }

    @PostMapping("")
    public ProjectDTO createProject(@RequestBody ProjectDTO project) {
        return this.projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO project) {
        return this.projectService.updateProject(id, project);
    }

    @PostMapping("/sprints")
    public SprintDTO createSprint(@RequestBody SprintDTO sprint) {
        User self = this.userService.getUserSelf();
        return this.projectService.createSprint(self, sprint);
    }
}
