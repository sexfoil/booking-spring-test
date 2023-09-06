package com.example.booking.service;

import com.example.booking.dao.EventDao;
import com.example.booking.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private EventDao eventDao;

    public Event getEventById(long id) {
        return eventDao.getEventById(id);
    }

    public List<Event> getEventsByTitle(String title) {
        return eventDao.getEventsByTitle(title);
    }

    public List<Event> getEventsForDay(Date day) {
        return eventDao.getEventsForDay(day);
    }

//    @JmsListener(destination = "booking-listener")
    public Event createEvent(Event event) {
        return eventDao.createEvent(event);
    }

    public Event updateEvent(Event event) {
        return eventDao.updateEvent(event);
    }

    public boolean deleteEvent(long eventId) {
        return eventDao.deleteEvent(eventId);
    }


    @Autowired
    void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
