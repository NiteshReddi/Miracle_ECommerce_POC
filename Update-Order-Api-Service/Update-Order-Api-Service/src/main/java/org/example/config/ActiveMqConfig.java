package org.example.config;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActiveMqConfig {

    @Value("${spring.activemq.broker.url}")
    private String brokerUrl;

//    @Bean
//    public ActiveMQConnectionFactory activeMQProducer() {
//        System.out.println("initializing ActiveMQConnectionFactory");
//        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//        System.out.println("Setting up broker "+brokerUrl);
//        activeMQConnectionFactory.setBrokerURL(brokerUrl);
//        return activeMQConnectionFactory;
//    }
//    @Bean
//    public ActiveMQConnectionFactory activeMQConsumer() {
//        System.out.println("initializing ActiveMQConsumer");
//        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//        System.out.println("Setting up broker "+brokerUrl);
//        activeMQConnectionFactory.setBrokerURL(brokerUrl);
//        return activeMQConnectionFactory;
//    }

    @Bean
    public ActiveMQComponent activeMQProducer() {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory());
        activeMQComponent.setConnectionFactory(pooledConnectionFactory);
        return activeMQComponent;
    }


    @Bean
    public ActiveMQComponent activeMQConsumer() {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory());

        activeMQComponent.setConnectionFactory(pooledConnectionFactory);

        return activeMQComponent;
    }


    private ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        System.out.println("Setting up broker "+brokerUrl);
        factory.setBrokerURL(brokerUrl);
        return factory;
    }


//    @Bean
//    public CachingConnectionFactory cachingConnectionFactory() {
//        return new CachingConnectionFactory(activeMQProducer());
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate() {
//        return new JmsTemplate(cachingConnectionFactory());
//    }

    @Bean
    public Queue orderUpdateQueue() {
        return new ActiveMQQueue("order.update.queue");
    }

}