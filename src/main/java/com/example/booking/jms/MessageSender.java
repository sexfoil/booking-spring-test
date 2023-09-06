package jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static Logger log = LoggerFactory.getLogger(MessageSender.class);

    public void sendTextMessage(String destination, String message) {
        log.info("Sending message to '{}' with text: '{}'", destination, message);

        jmsTemplate.send(destination, session -> session.createTextMessage(message));
    }
}
