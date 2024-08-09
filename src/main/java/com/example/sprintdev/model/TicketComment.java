package com.example.sprintdev.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_comments")
public class TicketComment {
    @Id
    @SequenceGenerator(name = "ticket_comments_seq", sequenceName = "ticket_comments_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_comments_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Lob
    private String content;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    public TicketComment() {}

    public TicketComment(Ticket ticket, User author, String content) {
        this.ticket = ticket;
        this.author = author;
        this.content = content;
        this.creationTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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
