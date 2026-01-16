package com.example.inventoryservice.kafka.consumer;

import com.example.inventoryservice.kafka.event.OrderCreatedEvent;
import com.example.inventoryservice.service.InventoryService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer{

    private static final Logger log = LoggerFactory.getLogger(InventoryConsumer.class);

    private final InventoryService inventoryService;

    public InventoryConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @org.springframework.retry.annotation.Backoff(delay = 3000),
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(
            topics = "order-created-topic",
            groupId = "inventory-group"
    )
    public void consume(
            OrderCreatedEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("Consumed message from topic={} | orderId={}", topic, event.getOrderId());
        inventoryService.reserveInventory(event);
    }

}
