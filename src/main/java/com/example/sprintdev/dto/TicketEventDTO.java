package com.example.sprintdev.dto;

import com.example.sprintdev.model.TicketEvent;

import java.time.LocalDateTime;

public class TicketEventDTO {
    private Long id;
    private Long ticketId;
    private UserDTO author;
    private String type;
    private String message;
    private String sizeVote;
    private LocalDateTime creationTime;

    public TicketEventDTO() {}

    public TicketEventDTO(TicketEvent event) {
        this.id = event.getId();
        this.ticketId = event.getTicket().getId();
        this.type = event.getType().toString();
        this.message = event.getMessage();
        this.author = new UserDTO(event.getAuthor());
        this.sizeVote = event.getSizeVote() != null? event.getSizeVote().toString() : null;
        this.creationTime = event.getCreationTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSizeVote() {
        return sizeVote;
    }

    public void setSizeVote(String sizeVote) {
        this.sizeVote = sizeVote;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
