package com.ecommerce.order_service.service;

import com.ecommerce.order_service.config.RabbitMQConfig;
import com.ecommerce.order_service.dto.OrderEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(OrderEvent event) {

        String message =
                "Order ID: " + event.getOrderId()
                        + ", Customer ID: " + event.getCustomerId();

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.QUEUE,
                message
        );
    }
}