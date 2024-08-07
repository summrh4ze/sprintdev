package com.example.sprintdev.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_events")
public class TicketEvent {
    @Id
    @SequenceGenerator(name = "ticket_events_seq", sequenceName = "ticket_events_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_events_seq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private TicketEventType type;

    @Lob
    private String message;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "size_vote")
    private TicketSize sizeVote = null;

    public TicketEvent() {}

    public TicketEvent(Ticket ticket, User author, TicketEventType type) {
        this();
        this.creationTime = LocalDateTime.now();
        this.author = author;
        this.ticket = ticket;
        this.type = type;
    }

    public TicketEvent(Ticket ticket, User author, TicketEventType type, String message) {
        this(ticket, author, type);
        this.message = message;
    }

    public TicketEvent(Ticket ticket, User author, TicketEventType type, TicketSize size, String message) {
        this(ticket, author, type, message);
        this.sizeVote = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketEventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public User getAuthor() {
        return author;
    }

    public TicketSize getSizeVote() {
        return sizeVote;
    }
}
