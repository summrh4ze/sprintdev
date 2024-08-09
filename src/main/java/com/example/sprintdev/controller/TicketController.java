package com.example.sprintdev.controller;

import com.example.sprintdev.dto.ApprovalVoteRequest;
import com.example.sprintdev.dto.TicketDTO;
import com.example.sprintdev.model.TicketSize;
import com.example.sprintdev.model.User;
import com.example.sprintdev.service.TicketService;
import com.example.sprintdev.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;

    public TicketController(
            TicketService ticketService,
            UserService userService
    ) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<TicketDTO> getAllTickets(@RequestParam(required = false) Long projectId, @RequestParam(required = false) Long sprintId) {
        return this.ticketService.getAllTickets(projectId, sprintId);
    }

    @GetMapping("/{id}")
    public TicketDTO getTicketById(@PathVariable Long id) {
        return this.ticketService.getTicketById(id);
    }

    @PostMapping("")
    public TicketDTO createTicket(@RequestBody TicketDTO ticket) {
        User self = this.userService.getUserSelf();
        return this.ticketService.createTicket(self, ticket);
    }

    @PutMapping("/{id}")
    public TicketDTO updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketChanges) {
        User self = this.userService.getUserSelf();
        return this.ticketService.updateTicket(id, self, ticketChanges);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id) {
        this.ticketService.deleteTicket(id);
    }

    @PutMapping("/{id}/approve")
    public TicketDTO approveTicket(@PathVariable Long id, @RequestBody String message) {
        User self = this.userService.getUserSelf();
        return this.ticketService.approveTicket(id, self, message);
    }

    @PutMapping("/{id}/approveFinal")
    public TicketDTO finalApprove(@PathVariable Long id, @RequestBody ApprovalVoteRequest body) {
        User self = this.userService.getUserSelf();
        return this.ticketService.finalApprove(id, self, body.getMessage());
    }

    @PutMapping("/{id}/vote")
    public TicketDTO voteSize(@PathVariable Long id, @RequestBody ApprovalVoteRequest body) {
        User self = this.userService.getUserSelf();
        return this.ticketService.sizeVote(id, self, TicketSize.valueOf(body.getSize()), body.getMessage());
    }

    @PutMapping("/{id}/voteFinal")
    public TicketDTO voteFinal(@PathVariable Long id, @RequestBody ApprovalVoteRequest body) {
        User self = this.userService.getUserSelf();
        return this.ticketService.sizeVoteFinal(id, self, TicketSize.valueOf(body.getSize()), body.getMessage());
    }
}
