package com.ecommerce.discount.client;

import com.ecommerce.common.domain.BusinessException;
import com.ecommerce.common.domain.Coupon;
import com.ecommerce.common.domain.CouponCreateRequest;
import com.ecommerce.common.domain.CouponCreateResponse;
import com.ecommerce.discount.client.configuration.UrlProperties;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static com.ecommerce.common.domain.faultcode.CouponFaultCode.COUPON_NOT_FOUND;
import static com.ecommerce.common.domain.faultcode.DiscountFaultCode.DISCOUNT_EXCEPTION;

@RequiredArgsConstructor
public class CouponServiceClient {

    private final RestTemplate restTemplate;

    public CouponCreateResponse createCoupon(CouponCreateRequest couponCreateRequest) {
        try {
            HttpEntity<CouponCreateRequest> httpEntity = new HttpEntity<>(couponCreateRequest);
            ResponseEntity<CouponCreateResponse> response = restTemplate.exchange(UrlProperties.Coupon.CREATE_COUPON_URL, HttpMethod.POST, httpEntity, CouponCreateResponse.class);
            return response.getBody();
        } catch (Exception e) {
            throw new BusinessException(DISCOUNT_EXCEPTION);
        }
    }

    public Coupon getCouponById(Long couponId) {
        try {
            Map<String, String> uriParams = Maps.newHashMap();
            uriParams.put("couponId", String.valueOf(couponId));
            ResponseEntity<Coupon> response = restTemplate.exchange(UrlProperties.Coupon.GET_COUPON_BY_ID_URL, HttpMethod.GET, null, Coupon.class, uriParams);
            if (!response.hasBody()) {
                throw new BusinessException(COUPON_NOT_FOUND, couponId);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new BusinessException(DISCOUNT_EXCEPTION);
        }
    }

    public Optional<Coupon> getByThresholdOrThresholdGreater(BigDecimal price) {
        try {
            URI uri = UriComponentsBuilder.fromUriString(UrlProperties.Coupon.GET_THRESHOLD_FILTER_URL)
                    .queryParam("price", String.valueOf(price))
                    .build().toUri();
            ResponseEntity<Coupon> response = restTemplate.exchange(uri, HttpMethod.GET, null, Coupon.class);
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            throw new BusinessException(DISCOUNT_EXCEPTION);
        }
    }
}
