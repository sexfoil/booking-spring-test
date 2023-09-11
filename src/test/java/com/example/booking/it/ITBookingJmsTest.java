package com.example.booking.it;

import com.example.booking.BookingApplication;
import com.example.booking.dao.EventDao;
import com.example.booking.dao.TicketDao;
import com.example.booking.dao.UserDao;
import com.example.booking.facade.BookingFacadeImpl;
import com.example.booking.jms.BookingMessageListener;
import com.example.booking.jms.BookingMessageSender;
import com.example.booking.repository.EventStorage;
import com.example.booking.repository.TicketStorage;
import com.example.booking.repository.UserStorage;
import com.example.booking.service.EventService;
import com.example.booking.service.TicketService;
import com.example.booking.service.UserService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import javax.jms.*;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { ITBookingJmsTest.TestConfig.class,
//        BookingMessageListener.class, BookingMessageSender.class, BookingFacadeImpl.class,
//        EventService.class, TicketService.class, UserService.class,
//        EventDao.class, TicketDao.class, UserDao.class,
//        EventStorage.class, TicketStorage.class, UserStorage.class,
//        String.class
//})
@AutoConfigureMockMvc
@SpringBootTest(classes = BookingApplication.class)
public class ITBookingJmsTest {


    @ClassRule
    public static GenericContainer<?> activeMqContainer = new GenericContainer<>(DockerImageName.parse("rmohr/activemq:5.18.2")).withExposedPorts(61616);

    @SpyBean
    private BookingMessageListener bookingMessageListener;

    @Autowired
    private BookingMessageSender bookingMessageSender;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void whenListening_thenReceivingCorrectMessage() throws JMSException {
        String queueName = BookingMessageListener.DESTINATION;
        String messageText = "Test message";

        jmsTemplate.send(queueName, s -> s.createTextMessage(messageText));

        ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);

        verify(bookingMessageListener, timeout(100))
                .bookingJmsListen(messageCaptor.capture());

        TextMessage receivedMessage = messageCaptor.getValue();
        assertEquals(messageText, receivedMessage.getText());
    }

    @Test
    public void whenSendingMessage_thenCorrectQueueAndMessageText() throws JMSException {
        String queueName = "some-destination";
        String messageText = "Test message";

        bookingMessageSender.sendTextMessage(queueName, messageText);

        Message sentMessage = jmsTemplate.receive(queueName);
        Assertions.assertThat(sentMessage)
                .isInstanceOf(TextMessage.class);

        assertEquals(messageText, ((TextMessage) sentMessage).getText());
    }

    @Test
    public void endpointTest() throws Exception {
        final String eventName = "MyEvent";
        final String expected = "CREATE MESSAGE: createEvent/" + eventName;

        MvcResult result = mockMvc.perform(get("/jms/create/{title}", eventName))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Configuration
    @EnableJms
    static class TestConfig {
        @Bean
        public JmsListenerContainerFactory<?> jmsListenerContainerFactory() {
            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory());
            return factory;
        }

        @Bean
        public ConnectionFactory connectionFactory() {
            return new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        }

        @Bean
        public JmsTemplate jmsTemplate() {
            return new JmsTemplate(connectionFactory());
        }
    }
}
