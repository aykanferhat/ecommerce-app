package com.ecommerce.core.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "total_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "basket_id", nullable = false)
    private Long basketId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @MapsId(value = "productId")
    @OneToOne
    private ProductEntity product;


    private List<BasketItemCampaign> basketItemCampaigns;

}
