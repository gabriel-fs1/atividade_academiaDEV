package repository.impl;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;
import model.SupportTicket;
import repository.SupportTicketRepository;

public class SupportTicketImpl implements SupportTicketRepository {

    private Queue<SupportTicket> st = new LinkedList<>();

    @Override
    public void save(SupportTicket supportTicket) {
        st.add(supportTicket);
    }

    @Override
    public Optional<SupportTicket> findAndDelete() {
        return Optional.ofNullable(st.poll());
    }
    
}
