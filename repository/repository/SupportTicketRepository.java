package repository;

import java.util.Optional;
import model.SupportTicket;

public interface SupportTicketRepository {

    void save(SupportTicket supportTicket);
    Optional<SupportTicket> findAndDelete();
    
}
