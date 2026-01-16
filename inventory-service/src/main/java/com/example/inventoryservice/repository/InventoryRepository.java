package com.example.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.inventoryservice.entity.Inventory;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(String product);
}
