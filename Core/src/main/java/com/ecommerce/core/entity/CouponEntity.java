package com.ecommerce.core.entity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "ecommerce", name = "coupon")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "threshold", nullable = false)
    private BigDecimal threshold;

    @Column(name = "discount_type", nullable = false)
    private String discountType;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;
}


