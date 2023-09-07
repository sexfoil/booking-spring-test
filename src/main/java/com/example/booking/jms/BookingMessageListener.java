package com.example.booking.jms;

import com.example.booking.facade.BookingFacade;
import com.example.booking.model.Event;
import com.example.booking.model.EventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;

@Component
public class BookingMessageListener {

    private static Logger log = LoggerFactory.getLogger(BookingMessageListener.class);

    public static final String DESTINATION = "booking-listener";

    @Autowired
    private BookingFacade bookingFacade;

    @JmsListener(destination = DESTINATION)
    public void bookingJmsListen(TextMessage message) throws JMSException {
        String textMessage = message.getText();
        log.info("Received message id='{}': {}", message.getJMSMessageID(), textMessage);
        String[] args = textMessage.split("/");
        switch (args[0].toLowerCase()) {
            case "createevent":
                Event event = new EventImpl();
                event.setTitle(args[1]);
                event.setDate(new Date());
                bookingFacade.createEvent(event);
                break;
            case "deleteevent":
                bookingFacade.deleteEvent(Long.parseLong(args[1]));
                break;
            case "geteventsbytitle":
                bookingFacade.getEventsByTitle(args[1], 10, 10);
                break;
            default:
                log.info("Unsupported endpoint");
        }
    }

}
