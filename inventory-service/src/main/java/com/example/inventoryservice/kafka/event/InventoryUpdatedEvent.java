package com.example.inventoryservice.kafka.event;

import java.io.Serializable;

public class InventoryUpdatedEvent implements Serializable {

    private Long orderId;
    private String status;

    public InventoryUpdatedEvent() {
        // Default constructor needed by Kafka (deserialization)
    }

    public InventoryUpdatedEvent(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
