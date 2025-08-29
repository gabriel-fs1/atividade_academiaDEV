package service;

import repository.SupportTicketRepository;

import java.util.List;
import java.util.Optional;

import Exceptions.AccessDeniedException;
import dtos.SupportTicketDTO;
import model.Admin;
import model.SupportTicket;
import model.User;

public class SupportTicketService {

    private final SupportTicketRepository str;

    public SupportTicketService(SupportTicketRepository str) {
        this.str = str;
    }

    public SupportTicketDTO addTicket(String title, String description, User author) {
        SupportTicket newTicket = new SupportTicket(author, description, title);
        this.str.save(newTicket);
        return toDto(newTicket);
    }

    public Optional<SupportTicketDTO> attendNextTicket(User admin) {
    if (!(admin instanceof Admin)) {
        throw new AccessDeniedException("Apenas administradores podem atender tickets.");
    }
        return str.processNext()
                .map(this::toDto); 
    }

    public List<SupportTicketDTO> findAll() {
    return str.findAll().stream()
               .map(this::toDto)
               .toList();
}

    private SupportTicketDTO toDto(SupportTicket ticket) {
        SupportTicketDTO dto = new SupportTicketDTO();

        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getMessage()); 
        dto.setEmailAuthor(ticket.getUser().getEmail()); 

        return dto;
    }
    
}
