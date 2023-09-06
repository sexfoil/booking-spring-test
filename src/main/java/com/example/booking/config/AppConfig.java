package com.example.booking.config;

import com.example.booking.facade.BookingFacade;
import com.example.booking.facade.BookingFacadeImpl;
import com.example.booking.repository.EventStorage;
import com.example.booking.repository.TicketStorage;
import com.example.booking.repository.UserStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
        return new BookingFacadeImpl();
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

}
