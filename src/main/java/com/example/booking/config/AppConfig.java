package config;

import dao.EventDao;
import dao.TicketDao;
import dao.UserDao;
import facade.BookingFacade;
import facade.BookingFacadeImpl;
import jms.MessageListener;
import jms.MessageSender;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import repository.EventStorage;
import repository.TicketStorage;
import repository.UserStorage;
import service.EventService;
import service.TicketService;
import service.UserService;

import javax.jms.ConnectionFactory;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${app.storage.event.path}")
    private String eventStorageFilePath;
    @Value("${app.storage.ticket.path}")
    private String ticketStorageFilePath;
    @Value("${app.storage.user.path}")
    private String userStorageFilePath;


    @Bean
    public BookingFacade bookingFacade() {
        return new BookingFacadeImpl(eventService(), ticketService(), userService(), messageListener());
    }


    @Bean
    public EventStorage eventStorage() {
        return new EventStorage(eventStorageFilePath);
    }

    @Bean
    public TicketStorage ticketStorage() {
        return new TicketStorage(ticketStorageFilePath);
    }

    @Bean
    public UserStorage userStorage() {
        return new UserStorage(userStorageFilePath);
    }

    @Bean
    public EventService eventService() {
        return new EventService();
    }

    @Bean
    public TicketService ticketService() {
        return new TicketService();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public EventDao eventDao() {
        return new EventDao();
    }

    @Bean
    public TicketDao ticketDao() {
        return new TicketDao();
    }

    @Bean
    public UserDao userDao() {
        return new UserDao();
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
//        return new ActiveMQConnectionFactory("tcp://localhost:61616");
//        return new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        return new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }

    @Bean
    public MessageSender messageSender() {
        return new MessageSender();
    }

}
