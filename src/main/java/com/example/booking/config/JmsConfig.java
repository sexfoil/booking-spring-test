package config;

import jms.MessageListener;
import jms.MessageSender;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

//@Configuration
//@EnableJms
//@ComponentScan
public class JmsConfig {

//    @Bean
//    public JmsListenerContainerFactory<?> jmsListenerContainerFactory() {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return new ActiveMQConnectionFactory("tcp://localhost:61616");
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate() {
//        return new JmsTemplate(connectionFactory());
//    }
//
//    @Bean
//    public MessageListener messageListener() {
//        return new MessageListener();
//    }
//
//    @Bean
//    public MessageSender messageSender() {
//        return new MessageSender();
//    }
}
