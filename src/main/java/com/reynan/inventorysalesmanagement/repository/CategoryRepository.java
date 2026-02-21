package com.reynan.inventorysalesmanagement.repository;

import com.reynan.inventorysalesmanagement.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
