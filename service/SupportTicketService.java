package service;

import repository.SupportTicketRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Exceptions.AccessDeniedException;
import dtos.EnrollmentExportDTO;
import dtos.SupportTicketDTO;
import dtos.TicketExportDTO;
import model.Admin;
import model.Enrollment;
import model.SupportTicket;
import model.User;

public class SupportTicketService {

    private final SupportTicketRepository str;

    public SupportTicketService(SupportTicketRepository str) {
        this.str = str;
    }

    public SupportTicketDTO addTicket(User author, String title, String description) {
        SupportTicket newTicket = new SupportTicket(author, title, description);
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

    public Collection<TicketExportDTO> getAllTicketsForExport() {
        return str.findAll().stream()
                .map(this::toExportDTO)
                .collect(Collectors.toList());
    }

    private SupportTicketDTO toDto(SupportTicket ticket) {
        SupportTicketDTO dto = new SupportTicketDTO();

        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setEmailAuthor(ticket.getUser().getEmail());

        return dto;
    }

    private TicketExportDTO toExportDTO(SupportTicket st) {
        TicketExportDTO dto = new TicketExportDTO();
        dto.setUser(st.getUser().getName());
        dto.setTitle(st.getTitle());
        dto.setDescription(st.getDescription());
        return dto;
    
    }

}
