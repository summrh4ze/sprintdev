package com.example.sprintdev.dto;

import com.example.sprintdev.model.TicketComment;

import java.time.LocalDateTime;

public class TicketCommentDTO {
    private Long id;
    private Long ticket;
    private UserDTO author;
    private String content;
    private LocalDateTime creationTime;

    public TicketCommentDTO() {}
    public TicketCommentDTO(TicketComment comment) {
        this.id = comment.getId();
        this.ticket = comment.getTicket().getId();
        this.author = new UserDTO(comment.getAuthor());
        this.content = comment.getContent();
        this.creationTime = comment.getCreationTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
