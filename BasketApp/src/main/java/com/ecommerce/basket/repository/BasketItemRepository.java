package com.ecommerce.basket.repository;

import com.ecommerce.core.entity.BasketItemEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketItemRepository extends JpaRepository<BasketItemEntity, Long> {

    List<BasketItemEntity> findByBasketId(Long basketId);

    Optional<BasketItemEntity> findByBasketIdAndProductId(Long basketId, Long productId);
}
