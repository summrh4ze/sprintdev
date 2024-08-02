package com.example.sprintdev.controller;

import com.example.sprintdev.dao.TicketDAO;
import com.example.sprintdev.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("")
    public List<TicketDAO> getAllTickets() {
        return this.ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDAO getTicketById(@PathVariable Long id) {
        return this.ticketService.getTicketById(id);
    }
}
