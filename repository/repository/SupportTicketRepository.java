package repository;

import java.util.List;
import java.util.Optional;
import model.SupportTicket;

public interface SupportTicketRepository {

    void save(SupportTicket supportTicket);
    Optional<SupportTicket> processNext();
    List<SupportTicket> findAll();
    
}
