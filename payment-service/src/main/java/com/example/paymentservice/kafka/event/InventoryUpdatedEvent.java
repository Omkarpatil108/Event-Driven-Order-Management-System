package com.example.paymentservice.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdatedEvent {

    private Long orderId;
    private String product;
    private Integer quantity;
    private Double amount;   // 🔴 REQUIRED
}
