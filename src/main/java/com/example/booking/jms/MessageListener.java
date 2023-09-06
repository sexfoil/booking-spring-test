package jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class MessageListener {

    private static Logger log = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "booking-listener")
    public void bookingJmsListen(TextMessage message) throws JMSException {
        log.info("Received message id='{}': {}", message.getJMSMessageID(), message.getText());
    }

    public void receiveMessage()  {
        try {
            log.info(jmsTemplate.receive("booking-listener").getJMSMessageID());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
