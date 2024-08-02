package com.example.sprintdev.repository;

import com.example.sprintdev.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface TicketRepository extends JpaRepository<Ticket, Long> {}