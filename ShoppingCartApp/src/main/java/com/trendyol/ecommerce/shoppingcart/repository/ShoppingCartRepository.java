package com.trendyol.ecommerce.shoppingcart.repository;

import com.trendyol.ecommerce.core.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
}
