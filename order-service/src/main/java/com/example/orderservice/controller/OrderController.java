package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {

        Order savedOrder = orderService.createOrder(
                order.getProduct(),
                order.getQuantity()
        );

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}
