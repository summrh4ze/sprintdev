package com.example.sprintdev.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @SequenceGenerator(name = "tickets_seq", sequenceName = "tickets_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tickets_seq")
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToOne
    @JoinColumn(name = "assignee_id")
    private User assignee = null;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sprint_id", referencedColumnName = "id")
    private Sprint sprint = null;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "size", nullable = false)
    private TicketSize size;

    @Lob
    private String content;

    @Lob
    private String errata;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketEvent> events = new ArrayList<>();

    public Ticket() {
        this.status = TicketStatus.CREATED;
        this.size = TicketSize.NOT_SIZED;
        this.creationTime = LocalDateTime.now();
    }

    public Ticket(Project project, User author, String title) {
        this();
        this.project = project;
        this.title = title;
        this.author = author;
        this.events.add(new TicketEvent(this, author, TicketEventType.CREATE));
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

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Project getProject() {
        return project;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public TicketSize getSize() {
        return size;
    }

    public String getContent() {
        return content;
    }

    public List<TicketComment> getComments() {
        return comments;
    }

    public List<TicketEvent> getEvents() {
        return events;
    }

    public User getAuthor() {
        return author;
    }

    public String getErrata() {
        return errata;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public User getAssignee() {
        return assignee;
    }

    public void updateTitle(User user, String title) {
        this.title = title;
        this.events.add(new TicketEvent(this, user, TicketEventType.TITLE_EDIT));
    }

    public void approveContent(User user, String message) {
        if (this.status != TicketStatus.CREATED) {
            throw new RuntimeException("ticket is not in status CREATED so it cannot be approved");
        }

        TicketEvent lastUpdate = getLastUpdate();

        boolean userHasAlreadyApproved = this.events
                .stream()
                .anyMatch(e -> e.getType() == TicketEventType.CONTENT_APPROVE &&
                        e.getCreationTime().isAfter(lastUpdate.getCreationTime()) &&
                        !Objects.equals(e.getAuthor().getId(), user.getId())
                );
        if (!userHasAlreadyApproved) {
            this.events.add(new TicketEvent(this, user, TicketEventType.CONTENT_APPROVE));
        }
        throw new RuntimeException("Already approved");
    }

    public void approveContentFinal(User user, List<User> allUsers, String message) {
        if (checkIfApprovedByMajorityPlusOne(allUsers)) {
            this.status = TicketStatus.ESTIMATION_READY;
            this.events.add(new TicketEvent(this, user, TicketEventType.CONTENT_APPROVE_FINAL, message));
            this.events.add(new TicketEvent(this, user, TicketEventType.STATUS_CHANGE, TicketStatus.ESTIMATION_READY.toString()));
        }
        throw new RuntimeException("Final approve failed because ticket is not approved by majority + 1");
    }

    public void updateContent(User user, String updatedContent) {
        if (this.status != TicketStatus.CREATED) {
            throw new RuntimeException("can't modify content once ticket got final approve");
        }
        this.content = updatedContent;
        this.events.add(new TicketEvent(this, user, TicketEventType.CONTENT_EDIT));
    }

    public void sizeVote(User user, TicketSize size, String message) {
        if (this.status != TicketStatus.ESTIMATION_READY) {
            throw new RuntimeException("can't size the ticket if not in ESTIMATION_READY status");
        }
        boolean userHasAlreadyVoted = this.events
                .stream()
                .anyMatch(e -> e.getType() == TicketEventType.SIZE_VOTE &&
                        !Objects.equals(e.getAuthor().getId(), user.getId())
                );
        if (!userHasAlreadyVoted) {
            this.events.add(new TicketEvent(this, user, TicketEventType.SIZE_VOTE, size, message));
        }
        throw new RuntimeException("Already approved");
    }

    public void finalSizeVote(User user, List<User> allUsers,  TicketSize size, String message) {
        if (checkIfSizeVotedByMajorityPlusOne(allUsers)) {
            this.status = TicketStatus.SPRINT_READY;
            this.size = size;
            this.events.add(new TicketEvent(this, user, TicketEventType.SIZE_VOTE_FINAL, size, message));
            this.events.add(new TicketEvent(this, user, TicketEventType.STATUS_CHANGE, TicketStatus.SPRINT_READY.toString()));
        }
    }

    public void changeStatus(User user, TicketStatus status) {
        if (this.status != status) {
            if (this.status == TicketStatus.CREATED || this.status == TicketStatus.ESTIMATION_READY) {
                throw new RuntimeException("CREATED and ESTIMATION_READY statuses can't be changed directly. Please use the approval/voting endpoints");
            } else if (this.status == TicketStatus.DONE) {
                throw new RuntimeException("Status of ticket with status DONE can't be changed. Please create a new ticket");
            } else if (status == TicketStatus.CREATED || status == TicketStatus.ESTIMATION_READY) {
                throw new RuntimeException("can't change back to CREATED or ESTIMATION_READY. Please create a new ticket if the content of this one is no longer valid");
            } else {
                this.status = status;
                this.events.add(new TicketEvent(this, user, TicketEventType.STATUS_CHANGE, status.toString()));
            }
        }
    }

    public void setSprint(User user, Sprint sprint) {
        this.sprint = sprint;
        this.events.add(new TicketEvent(this, user, TicketEventType.SPRINT_CHANGE, "Created sprint " + sprint.getName()));
        if (this.status == TicketStatus.SPRINT_READY) {
            this.status = TicketStatus.DEV_READY;
            this.events.add(new TicketEvent(this, user, TicketEventType.STATUS_CHANGE, TicketStatus.DEV_READY.toString()));
        }
    }

    public void addComment(TicketComment comment) {
        if (this.comments.stream().noneMatch(c -> Objects.equals(c.getId(), comment.getId()))) {
            this.comments.add(comment);
            this.events.add(new TicketEvent(this, comment.getAuthor(), TicketEventType.COMMENT_ADD));
        }
    }

    public void setErrata(String errata) {
        this.errata = errata;
    }

    public void setAssignee(User user, User assignee) {
        this.assignee = user;
        this.events.add(new TicketEvent(this, user, TicketEventType.ASSIGNEE_CHANGE, assignee.getUsername()));
    }

    private TicketEvent getLastUpdate() {
        return this.events
                .stream()
                .filter(e -> e.getType() == TicketEventType.CREATE || e.getType() == TicketEventType.CONTENT_EDIT)
                .max(Comparator.comparing(TicketEvent::getCreationTime))
                .orElseThrow(() -> new RuntimeException("Creation event missing for ticket " + this.title + " in project " + this.project.getName()));
    }

    private boolean checkIfApprovedByMajorityPlusOne(List<User> allUsers) {
        TicketEvent lastUpdate = getLastUpdate();
        long numberOfApprovals = events.stream()
                .filter(e -> e.getType() == TicketEventType.CONTENT_APPROVE && e.getCreationTime().isAfter(lastUpdate.getCreationTime()))
                .count();
        return numberOfApprovals >= allUsers.size() + 1;
    }

    private boolean checkIfSizeVotedByMajorityPlusOne(List<User> allUsers) {
        long numberOfVotes = events.stream().filter(e -> e.getType() == TicketEventType.SIZE_VOTE).count();
        return numberOfVotes >= allUsers.size() + 1;
    }
}
