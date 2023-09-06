package com.example.booking.controller;

import com.example.booking.facade.BookingFacade;
import com.example.booking.jms.MessageSender;
import com.example.booking.model.Event;
import com.example.booking.model.EventImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jms")
public class BookingFacadeController {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private BookingFacade bookingFacade;


    @Value("${app.listener.destination}")
    private String destination;


    @GetMapping("/events/{title}")
    public String getEventsByTitle(@PathVariable(value = "title") String title) {
        String message = "getEventsByTitle/" + title;
        messageSender.sendTextMessage(destination, message);
        List<Event> events = bookingFacade.getEventsByTitle("Event", 10, 10);
        StringBuilder sb = new StringBuilder("Events:<br>");
        events.forEach(e -> sb.append(e).append("<br>"));

        return sb.toString();
    }

    @GetMapping("/delete/{id}")
    public String tryJms(@PathVariable(value = "id") Long id) {
        String message = "deleteEvent/" + id;
        messageSender.sendTextMessage(destination, message);
        return "CREATE MESSAGE: " + message;
    }

    @GetMapping("/create/{title}")
    public String tryJms(@PathVariable(value = "title") String title) {
        String message = "createEvent/" + title;
        Event event = new EventImpl();
        event.setDate(new Date());
        event.setTitle(title);
        messageSender.sendTextMessage(destination, message);
        return "CREATE MESSAGE: " + message;
    }
}
