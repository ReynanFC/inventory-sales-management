package com.reynan.inventorysalesmanagement.repository;

import com.reynan.inventorysalesmanagement.entities.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem,Long> {
}
