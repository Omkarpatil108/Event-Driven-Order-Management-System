package com.example.orderservice.kafka.producer;

import com.example.orderservice.kafka.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;

public class OrderProducer{

    private final KafkaTemplate<String, OrderCreatedEvent>kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send("order-created-topic", event);
    }


}
