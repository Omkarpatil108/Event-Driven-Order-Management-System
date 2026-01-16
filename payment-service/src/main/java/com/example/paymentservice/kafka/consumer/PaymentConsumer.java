package com.example.paymentservice.consumer;

import com.example.paymentservice.event.InventoryUpdatedEvent;
import com.example.paymentservice.event.PaymentCompletedEvent;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = "inventory-updated-topic", groupId = "payment-group")
    public void consumeInventoryUpdatedEvent(InventoryUpdatedEvent event) {

        log.info("Received InventoryUpdatedEvent: {}", event);

        // ✅ NOW MATCHES METHOD SIGNATURE
        paymentService.processPayment(
                event.getOrderId(),
                event.getAmount()
        );

        log.info("Payment processed for orderId={}", event.getOrderId());
    }

    @KafkaListener(topics = "payment-completed-topic", groupId = "payment-group")
    public void consumePaymentCompletedEvent(PaymentCompletedEvent event) {
        log.info("Received PaymentCompletedEvent: {}", event);
    }
}
