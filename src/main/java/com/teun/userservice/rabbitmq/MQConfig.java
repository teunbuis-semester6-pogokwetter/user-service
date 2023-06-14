package com.teun.userservice.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    static final String QUEUE_USER_DELETE = "user_delete_queue";

    static final String EXCHANGE_USER_DELETE = "user_exchange_delete";

    static final String ROUTINGKEY_USER_DELETE = "user_delete_routingkey";

    @Bean
    Queue userDeleteQueue(){
        return new Queue(QUEUE_USER_DELETE, true);
    }
    @Bean
    TopicExchange userDeleteExchange(){
        return new TopicExchange(EXCHANGE_USER_DELETE);
    }
    @Bean
    Binding bindingUserDelete(){
        return BindingBuilder.bind(userDeleteQueue()).to(userDeleteExchange()).with(ROUTINGKEY_USER_DELETE);
    }
}
