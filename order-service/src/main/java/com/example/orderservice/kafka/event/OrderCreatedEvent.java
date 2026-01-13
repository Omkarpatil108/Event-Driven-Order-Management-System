package com.example.orderservice.kafka.event;

public class OrderCreatedEvent {

    private Long orderId;
    private String product;
    private int quantity;

    // ✅ Required for Kafka / Jackson
    public OrderCreatedEvent() {
    }

    // ✅ This fixes your error
    public OrderCreatedEvent(Long orderId, String product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    // getters & setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
