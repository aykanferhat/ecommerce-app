package com.ecommerce.core.entity;

import com.ecommerce.core.domain.DiscountType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "ecommerce", name = "campaign")
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "threshold", nullable = false)
    private Integer threshold;

    @Column(name = "discount_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;
}
