package com.ecommerce.shoppingcart.repository;

import com.ecommerce.core.entity.ShoppingCartItemEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItemEntity, Long> {

    List<ShoppingCartItemEntity> findByShoppingCartId(Long shoppingCartId);

    Optional<ShoppingCartItemEntity> findByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);
}
