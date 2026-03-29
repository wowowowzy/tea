package com.example.tea.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class RabbitMQCommunityConfig {
    public static final String COMMUNITY_QUEUE = "community.queue";
    public static final String COMMUNITY_EXCHANGE = "community.exchange";
    public static final String COMMUNITY_ROUTING_KEY = "community.routing.key";
    @Bean
    public MessageConverter msgConverter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(
                Collections.singletonList("com.example.tea.entity.dto.*")
        );
        return converter;
    }
    @Bean
    public Queue communityQueue() {
        return QueueBuilder.durable(COMMUNITY_QUEUE).build();
    }
    @Bean
    public DirectExchange communityExchange() {
        return new DirectExchange(COMMUNITY_EXCHANGE, true, false);
    }
    @Bean
    public Binding communityBinding() {
        return BindingBuilder.bind(communityQueue())
                .to(communityExchange())
                .with(COMMUNITY_ROUTING_KEY);
    }
}
