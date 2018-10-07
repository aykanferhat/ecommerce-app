package com.trendyol.ecommerce.product.repository;

import com.trendyol.ecommerce.core.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
