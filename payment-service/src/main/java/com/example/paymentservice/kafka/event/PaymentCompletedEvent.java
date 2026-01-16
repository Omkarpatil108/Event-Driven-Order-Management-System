package com.example.paymentservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentCompletedEvent {
    private Long orderId;
    private Double amount;
    private String status; // e.g., "COMPLETED"
}
