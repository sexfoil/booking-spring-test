package com.example.booking.dao;

import com.example.booking.model.User;
import com.example.booking.repository.TicketStorage;
import com.example.booking.model.Event;
import com.example.booking.model.Ticket;
import com.example.booking.model.TicketImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TicketDao {


    private TicketStorage ticketStorage;

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        long id = ticketStorage.getNextId();
        Ticket ticket = new TicketImpl();
        ticket.setId(id);
        ticket.setUserId(userId);
        ticket.setEventId(eventId);
        ticket.setPlace(place);
        ticket.setCategory(category);
        ticketStorage.getStorageMap().put(id, ticket);

        return ticketStorage.getStorageMap().get(id);
    }

    public List<Ticket> getBookedTickets(User user) {
        return ticketStorage.getStorageMap().values().stream()
                .filter(ticket -> ticket.getUserId() == user.getId())
                .collect(Collectors.toList());
    }

    public List<Ticket> getBookedTickets(Event event) {
        return ticketStorage.getStorageMap().values().stream()
                .filter(ticket -> ticket.getEventId() == event.getId())
                .collect(Collectors.toList());
    }

    public boolean cancelTicket(long ticketId) {
        Ticket removed = ticketStorage.getStorageMap().remove(ticketId);
        return removed != null;
    }

    @Autowired
    public void setTicketStorage(TicketStorage ticketStorage) {
        this.ticketStorage = ticketStorage;
    }
}
