package com.example.sprintdev.dao;

import com.example.sprintdev.model.Ticket;

import java.time.LocalDateTime;

public class TicketDAO {
    private Long id;
    private String name;
    private LocalDateTime creationTime;

    public TicketDAO(Ticket ticket) {
        this.id = ticket.getId();
        this.name = ticket.getName();
        this.creationTime = ticket.getCreationTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
