package com.ecommerce.basket.repository;

import com.ecommerce.core.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
}
