package com.ecommerce.common.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Builder
public class CouponCreateResponse extends ResourceSupport {

    private Long couponId;
}
