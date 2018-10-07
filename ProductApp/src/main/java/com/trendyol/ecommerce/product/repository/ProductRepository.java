package com.trendyol.ecommerce.product.repository;

import com.trendyol.ecommerce.core.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
