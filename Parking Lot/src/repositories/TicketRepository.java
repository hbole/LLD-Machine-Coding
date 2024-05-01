package repositories;

import models.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TicketRepository {
    //DB Queries for Ticket
    private Map<Long, Ticket> tickets = new HashMap<>();
    private Long ticketId;

    public Optional<Ticket> findTicketById(Long ticketId) {
        if(tickets.containsKey(ticketId)) {
            return Optional.of(tickets.get(ticketId));
        }
        return Optional.empty();
    }

    public Ticket save(Ticket ticket) {
        ticketId++;

        ticket.setId(ticketId);
        tickets.put(ticketId, ticket);
        return ticket;
    }
}
