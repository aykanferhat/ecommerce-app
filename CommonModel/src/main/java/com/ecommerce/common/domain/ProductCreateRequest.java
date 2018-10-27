package com.ecommerce.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {

    private String titleCode;
    private Long pictureKey;
    private List<Long> pictureKeys;
    private Map<Locale, Long> descriptionKey;
    private Long categoryId;
}
