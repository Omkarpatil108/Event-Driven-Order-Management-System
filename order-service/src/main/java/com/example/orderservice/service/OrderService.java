package com.example.orderservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.kafka.event.OrderCreatedEvent;
import com.example.orderservice.kafka.producer.OrderProducer;
import com.example.orderservice.repository.OrderRepository;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository,
                        OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    public Order createOrder(String product, int quantity) {
        log.info("Creating order | product={} | quantity={}", product, quantity);

        // 1️⃣ Create Order entity
        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setStatus("CREATED");

        // 2️⃣ Save to DB
        Order savedOrder = orderRepository.save(order);
        log.info("Order saved | orderId={}", savedOrder.getId());

        // 3️⃣ Create Kafka Event
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getProduct(),
                savedOrder.getQuantity()
        );

        // 4️⃣ Publish event
        log.info("Publishing OrderCreatedEvent | orderId={}", savedOrder.getId());
        orderProducer.sendOrderCreatedEvent(event);

        return savedOrder;
    }
}
