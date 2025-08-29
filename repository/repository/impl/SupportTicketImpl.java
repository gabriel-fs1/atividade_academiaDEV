package repository.impl;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;
import java.util.List;

import model.SupportTicket;
import repository.SupportTicketRepository;

public class SupportTicketImpl implements SupportTicketRepository {

    private Queue<SupportTicket> st = new LinkedList<>();

    @Override
    public void save(SupportTicket supportTicket) {
        st.add(supportTicket);
    }

    @Override
    public Optional<SupportTicket> processNext() {
        return Optional.ofNullable(st.poll());
    }

    @Override
    public List<SupportTicket> findAll() {
        return st.stream().toList();
    }
    
}
