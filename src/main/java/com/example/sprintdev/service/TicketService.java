package com.example.sprintdev.service;

import com.example.sprintdev.dao.TicketDAO;
import com.example.sprintdev.model.Ticket;
import com.example.sprintdev.repository.TicketRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<TicketDAO> getAllTickets() {
        return this.ticketRepository
                .findAll()
                .stream()
                .map(TicketDAO::new)
                .collect(Collectors.toList());
    }

    public TicketDAO getTicketById(long id) {
        return this.ticketRepository.findById(id).map(TicketDAO::new).orElseThrow();
    }
}
