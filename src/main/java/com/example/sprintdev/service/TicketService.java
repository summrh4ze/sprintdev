package com.example.sprintdev.service;

import com.example.sprintdev.dto.TicketDTO;
import com.example.sprintdev.model.*;
import com.example.sprintdev.repository.ProjectRepository;
import com.example.sprintdev.repository.SprintRepository;
import com.example.sprintdev.repository.TicketRepository;
import com.example.sprintdev.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;

    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository,
            SprintRepository sprintRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.sprintRepository = sprintRepository;
    }

    @Secured({"ROLE_PO", "ROLE_SM", "ROLE_DEV", "ROLE_DEV_LEAD", "ROLE_QA", "ROLE_QA_LEAD"})
    @Transactional
    public List<TicketDTO> getAllTickets(Long projectId, Long sprintId) {
        if (projectId == null) {
            return this.ticketRepository
                    .findAll()
                    .stream()
                    .map(TicketDTO::new)
                    .toList();
        }
        if (sprintId == null) {
            return this.ticketRepository
                    .findByProjectId(projectId)
                    .stream()
                    .map(TicketDTO::new)
                    .toList();
        }

        return this.ticketRepository
                .findByProjectIdAndSprintId(projectId, sprintId)
                .stream()
                .map(TicketDTO::new)
                .toList();
    }

    @Secured({"ROLE_PO", "ROLE_SM", "ROLE_DEV", "ROLE_DEV_LEAD", "ROLE_QA", "ROLE_QA_LEAD"})
    @Transactional
    public TicketDTO getTicketById(long id) {
        return this.ticketRepository.findById(id).map(TicketDTO::new).orElseThrow();
    }

    @Secured({"ROLE_PO", "ROLE_DEV", "ROLE_DEV_LEAD"})
    @Transactional
    public TicketDTO updateTicket(Long ticketId, User user, TicketDTO ticketChanges) {
        Ticket ticket = this.ticketRepository.findById(ticketChanges.getId()).orElseThrow();
        ticketChanges.getComments().forEach(ticketComment -> {
            TicketComment comment = new TicketComment(ticket, user, ticketComment.getContent());
            ticket.addComment(comment);
        });
        if (ticketChanges.getSprint() != null && ticketChanges.getSprint().getId() != null) {
            Sprint sprint = this.sprintRepository.findById(ticketChanges.getSprint().getId()).orElseThrow();
            ticket.setSprint(user, sprint);
        }
        if (ticketChanges.getAssignee() != null) {
            User assignee = this.userRepository.findById(ticketChanges.getAssignee().getId()).orElseThrow();
            ticket.setAssignee(user, assignee);
        }
        ticket.updateTitle(user, ticketChanges.getTitle());
        ticket.updateContent(user, ticket.getContent());
        ticket.setErrata(ticket.getErrata());
        ticket.changeStatus(user, TicketStatus.valueOf(ticketChanges.getStatus()));
        Ticket savedTicket = this.ticketRepository.save(ticket);
        return new TicketDTO(savedTicket);
    }

    @Secured({"ROLE_PO", "ROLE_SM", "ROLE_DEV", "ROLE_DEV_LEAD", "ROLE_QA", "ROLE_QA_LEAD"})
    @Transactional
    public TicketDTO createTicket(User user, TicketDTO ticket) {
        Project project = this.projectRepository.findById(ticket.getProject().getId()).orElseThrow();
        Ticket newTicket = new Ticket(project, user, ticket.getTitle());
        Ticket savedTicket = this.ticketRepository.save(newTicket);
        return new TicketDTO(savedTicket);
    }

    @Secured({"ROLE_DEV", "ROLE_DEV_LEAD"})
    @Transactional
    public TicketDTO approveTicket(Long ticketId, User user, String message) {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow();
        ticket.approveContent(user, message);
        Ticket updatedTicket = this.ticketRepository.save(ticket);
        return new TicketDTO(updatedTicket);
    }

    @Secured({"ROLE_DEV", "ROLE_DEV_LEAD"})
    @Transactional
    public TicketDTO finalApprove(Long ticketId, User user, String message) {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow();
        List<User> devs = this.userRepository.findByRoles(List.of(UserRole.DEV_LEAD, UserRole.DEV));
        ticket.approveContentFinal(user, devs, message);
        Ticket updatedTicket = this.ticketRepository.save(ticket);
        return new TicketDTO(updatedTicket);
    }

    @Secured({"ROLE_DEV", "ROLE_DEV_LEAD"})
    @Transactional
    public TicketDTO sizeVote(Long ticketId, User user, TicketSize size, String message) {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow();
        ticket.sizeVote(user, size, message);
        Ticket updatedTicket = this.ticketRepository.save(ticket);
        return new TicketDTO(updatedTicket);
    }

    @Secured({"ROLE_DEV", "ROLE_DEV_LEAD"})
    @Transactional
    public TicketDTO sizeVoteFinal(Long ticketId, User user, TicketSize size, String message) {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow();
        List<User> devs = this.userRepository.findByRoles(List.of(UserRole.DEV_LEAD, UserRole.DEV));
        ticket.finalSizeVote(user, devs, size, message);
        Ticket updatedTicket = this.ticketRepository.save(ticket);
        return new TicketDTO(updatedTicket);
    }
}
