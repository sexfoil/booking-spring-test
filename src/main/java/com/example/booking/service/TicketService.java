package service;

import dao.TicketDao;
import model.Event;
import model.Ticket;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private TicketDao ticketDao;

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketDao.bookTicket(userId, eventId, place, category);
    }

    public List<Ticket> getBookedTickets(User user) {
        return ticketDao.getBookedTickets(user);
    }

    public List<Ticket> getBookedTickets(Event event) {
        return ticketDao.getBookedTickets(event);
    }

    public boolean cancelTicket(long ticketId) {
        return ticketDao.cancelTicket(ticketId);
    }


    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
