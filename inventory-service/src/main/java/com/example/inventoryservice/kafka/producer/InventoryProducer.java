package com.example.inventoryservice.kafka.producer;

import com.example.inventoryservice.kafka.event.InventoryUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryProducer {

    private final KafkaTemplate<String, InventoryUpdatedEvent> kafkaTemplate;

    public InventoryProducer(KafkaTemplate<String, InventoryUpdatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendInventoryUpdatedEvent(InventoryUpdatedEvent event) {
        // Publish event to "inventory-updated-topic"
        kafkaTemplate.send("inventory-updated-topic", event);
        System.out.println("Published InventoryUpdatedEvent for order: " + event.getOrderId());
    }
}
