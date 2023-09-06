package com.example.booking.facade;

import com.example.booking.model.User;
import com.example.booking.model.Event;
import com.example.booking.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import com.example.booking.service.EventService;
import com.example.booking.service.TicketService;
import com.example.booking.service.UserService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.List;

@Component
public class BookingFacadeImpl implements BookingFacade {

    private static Logger log = LoggerFactory.getLogger(BookingFacadeImpl.class);

    @Autowired
    private EventService eventService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserService userService;

    @Override
    public Event getEventById(long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            log.error("Event with id='" + id + "' was not found.");
        }
        return event;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.info("Searching Events by key words '" + title + "' ...");
        List<Event> events = eventService.getEventsByTitle(title);
        log.info("Result: " + events);
        return events;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.info("Searching Events for day '" + day + "' ...");
        List<Event> events = eventService.getEventsForDay(day);
        log.info("Result: " + events);
        return events;
    }

    @Override
    public Event createEvent(Event event) {
        Event newEvent = eventService.createEvent(event);
        log.info("Event '" + newEvent + "' was created.");
        return newEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        Event updatedEvent = eventService.updateEvent(event);
        log.info("Event '" + updatedEvent + "' was " + (updatedEvent == null ? "" : "not ") + "updated.");
        return updatedEvent;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        boolean isDeleted = eventService.deleteEvent(eventId);
        log.info("Event with id='" + eventId + "' was " + (isDeleted ? "" : "not ") + "deleted.");
        return isDeleted;
    }

    @Override
    public User getUserById(long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            log.error("User with id='" + id + "' was not found.");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            log.error("User with email='" + email + "' was not found.");
        }
        return user;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("Searching Users by name '" + name + "' ...");
        List<User> users = userService.getUsersByName(name);
        log.info("Result: " + users);
        return users;
    }

    @Override
    public User createUser(User user) {
        User newUser = userService.createUser(user);
        log.info("User '" + newUser + "' was created.");
        return newUser;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = userService.updateUser(user);
        log.info("User '" + updatedUser + "' was updated.");
        return updatedUser;
    }

    @Override
    public boolean deleteUser(long userId) {
        boolean isDeleted = userService.deleteUser(userId);
        log.info("User with id='" + userId + "' was " + (isDeleted ? "" : "not ") + "deleted.");
        return isDeleted;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return null;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        log.info("Searching Tickets by User '" + user + "' ...");
        List<Ticket> tickets = ticketService.getBookedTickets(user);
        log.info("Result: " + tickets);
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.info("Searching Tickets by Event '" + event + "' ...");
        List<Ticket> tickets = ticketService.getBookedTickets(event);
        log.info("Result: " + tickets);
        return tickets;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        boolean isCanceled = ticketService.cancelTicket(ticketId);
        log.info("Ticket with id='" + ticketId + "' was " + (isCanceled ? "" : "not ") + "canceled.");
        return isCanceled;
    }
}
