package com.example.sprintdev.dto;

import com.example.sprintdev.model.Sprint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SprintDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> ticketIds = new ArrayList<>();

    public SprintDTO() {}

    public SprintDTO(Sprint sprint) {
        this.id = sprint.getId();
        this.name = sprint.getName();
        this.description = sprint.getDescription();
        this.startDate = sprint.getStartDate();
        this.endDate = sprint.getEndDate();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Long> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Long> ticketIds) {
        this.ticketIds = ticketIds;
    }
}
