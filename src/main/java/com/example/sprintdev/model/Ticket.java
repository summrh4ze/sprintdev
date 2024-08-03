package com.example.sprintdev.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @SequenceGenerator(name = "tickets_seq", sequenceName = "tickets_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tickets_seq")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    public Ticket() {}

    public Ticket(String name, LocalDateTime creationTime) {
        this.name = name;
        this.creationTime = creationTime;
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
