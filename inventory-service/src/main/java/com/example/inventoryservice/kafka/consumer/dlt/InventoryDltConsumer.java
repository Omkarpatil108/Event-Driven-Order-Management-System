package com.example.inventoryservice.kafka.consumer.dlt;

import com.example.inventoryservice.kafka.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class InventoryDltConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryDltConsumer.class);

    @KafkaListener(
            topics = "order-created-topic.DLT",
            groupId = "inventory-dlt-group"
    )
    public void consumeFromDlt(
            OrderCreatedEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.error(
                "🚨 DLT MESSAGE RECEIVED | topic={} | partition={} | offset={} | orderId={}",
                topic, partition, offset, event.getOrderId()
        );

        // IMPORTANT:
        // ❌ Do NOT throw exception here
        // ❌ Do NOT retry
        // ✅ Just log for now
    }
}
