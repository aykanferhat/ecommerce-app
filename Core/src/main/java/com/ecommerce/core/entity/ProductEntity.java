package com.ecommerce.core.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "ecommerce", name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String titleCode;

    @Column(name = "picture_path")
    private Long pictureKey;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_picture_keys", schema = "ecommerce")
    private List<Long> pictureKeys;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyColumn(name="locale")
    @Column(name="description_id")
    @CollectionTable(name="product_description_key", joinColumns= @JoinColumn(name="id"), schema = "ecommerce")
    private Map<Locale, Long> descriptionKey;

    @Column(name = "category_id")
    private Long categoryId;
}
