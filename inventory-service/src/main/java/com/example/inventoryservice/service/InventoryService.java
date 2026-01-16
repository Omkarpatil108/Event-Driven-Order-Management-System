package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.kafka.event.OrderCreatedEvent;
import com.example.inventoryservice.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // Process Kafka event atomically
    @Transactional
    public void reserveInventory(OrderCreatedEvent event) {

        log.info("Received OrderCreatedEvent | orderId={} | product={} | quantity={}",
                event.getOrderId(), event.getProduct(), event.getQuantity());

        // Fetch inventory row for product
        Inventory inventory = inventoryRepository.findByProduct(event.getProduct())
                .orElseGet(() -> new Inventory(null, event.getProduct(), 100, null));

        // ✅ Atomic idempotency check
        if (event.getOrderId().equals(inventory.getLastProcessedOrderId())) {
            log.warn("Duplicate event ignored | orderId={}", event.getOrderId());
            return;
        }

        // Check stock availability
        if (inventory.getQuantity() < event.getQuantity()) {
            log.warn("Not enough stock | orderId={} | product={} | available={}",
                    event.getOrderId(), event.getProduct(), inventory.getQuantity());
            return;
        }

        // Deduct stock and mark event processed
        inventory.setQuantity(inventory.getQuantity() - event.getQuantity());
        inventory.setLastProcessedOrderId(event.getOrderId());

        // Save atomically
        inventoryRepository.save(inventory);

        log.info("Inventory reserved | orderId={} | product={} | remaining={}",
                event.getOrderId(), event.getProduct(), inventory.getQuantity());
    }
}
