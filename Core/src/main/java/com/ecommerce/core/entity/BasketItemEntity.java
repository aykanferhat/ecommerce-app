package com.ecommerce.core.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "ecommerce", name = "basket_item")
public class BasketItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "total_quantity")
    private Integer quantity;

    @Column(name = "basket_id")
    private Long basketId;

    @Column(name = "product_id")
    private Long productId;

    @MapsId(value = "productId")
    @OneToOne
    private ProductEntity product;

}
