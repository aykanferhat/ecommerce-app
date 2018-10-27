package com.ecommerce.common.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

@Getter
@Builder
public class BasketCreateResponse extends ResourceSupport {

    private Long basketId;
    private Date creationDate;
    private Long userId;
}
