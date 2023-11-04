package com.example.casestudy.config;

import com.example.casestudy.constants.Constants;
import com.example.casestudy.constants.Topics;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(Constants.TOPIC_EXCHANGE);
    }

    @Bean
    public Queue startBiddingQueue(){
        return new Queue(Constants.BIDDING_START_QUEUE);
    }

    @Bean Queue closeBiddingQueue(){
        return new Queue(Constants.BIDDING_CLOSE_QUEUE);
    }


    @Bean
    public Binding bindingBiddingStartQueue(Queue startBiddingQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(startBiddingQueue).to(topicExchange).with(Topics.START_BIDDING);
    }

    @Bean
    public Binding bindingBiddingCloseQueue(Queue closeBiddingQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(closeBiddingQueue).to(topicExchange).with(Topics.CLOSE_BIDDING);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
