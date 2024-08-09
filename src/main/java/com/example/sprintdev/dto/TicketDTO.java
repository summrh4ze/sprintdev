package com.example.sprintdev.dto;

import com.example.sprintdev.model.Ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDTO {
    private Long id;
    private ProjectDTO project;
    private SprintDTO sprint;
    private UserDTO author;
    private UserDTO assignee;
    private String title;
    private LocalDateTime creationTime;
    private String status;
    private String size;
    private String content;
    private String errata;
    private List<TicketCommentDTO> comments = new ArrayList<>();
    private List<TicketEventDTO> events = new ArrayList<>();

    public TicketDTO() {}

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.project = new ProjectDTO(ticket.getProject());
        if (ticket.getSprint() != null) {
            this.sprint = new SprintDTO(ticket.getSprint());
        }
        this.author = new UserDTO(ticket.getAuthor());
        if (ticket.getAssignee() != null) {
            this.assignee = new UserDTO(ticket.getAssignee());
        }
        this.title = ticket.getTitle();
        this.creationTime = ticket.getCreationTime();
        this.status = ticket.getStatus().toString();
        this.size = ticket.getSize().toString();
        this.content = ticket.getContent();
        this.errata = ticket.getErrata();
        this.comments = ticket.getComments().stream().map(TicketCommentDTO::new).toList();
        this.events = ticket.getEvents().stream().map(TicketEventDTO::new).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getErrata() {
        return errata;
    }

    public void setErrata(String errata) {
        this.errata = errata;
    }

    public List<TicketCommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<TicketCommentDTO> comments) {
        this.comments = comments;
    }

    public List<TicketEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<TicketEventDTO> events) {
        this.events = events;
    }

    public SprintDTO getSprint() {
        return sprint;
    }

    public void setSprint(SprintDTO sprint) {
        this.sprint = sprint;
    }

    public UserDTO getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDTO assignee) {
        this.assignee = assignee;
    }
}
