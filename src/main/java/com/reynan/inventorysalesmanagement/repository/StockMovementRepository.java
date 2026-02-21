package com.reynan.inventorysalesmanagement.repository;

import com.reynan.inventorysalesmanagement.entities.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
}
