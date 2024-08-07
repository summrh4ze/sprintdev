package com.example.sprintdev.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "sprints")
public class Sprint {
    @Id
    @SequenceGenerator(name = "sprints_seq", sequenceName = "sprints_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sprints_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Sprint() {}

    public Sprint(String name, String description, LocalDate endDate) {
        this();
        this.name = name;
        this.description = description;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
