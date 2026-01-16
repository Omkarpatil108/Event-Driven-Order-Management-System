package com.example.paymentservice.service;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.event.PaymentCompletedEvent;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentService(PaymentRepository paymentRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Payment processPayment(Long orderId, Double amount) {
        Payment payment = new Payment(orderId, amount, "COMPLETED");
        Payment savedPayment = paymentRepository.save(payment);

        // Publish PaymentCompletedEvent
        PaymentCompletedEvent event = new PaymentCompletedEvent(orderId, amount, "COMPLETED");
        kafkaTemplate.send("payment-completed", event);

        System.out.println("Payment processed for order: " + orderId);
        return savedPayment;
    }
}
