package com.ecommerce.basket.service;

import com.ecommerce.core.domain.Coupon;
import com.ecommerce.core.domain.Basket;
import com.ecommerce.core.domain.BasketItem;
import com.ecommerce.basket.calculator.BasketCalculator;
import com.ecommerce.basket.mapper.BasketMapper;
import com.ecommerce.basket.model.request.BasketCreateRequest;
import com.ecommerce.basket.model.response.BasketCreateResponse;
import com.ecommerce.basket.repository.BasketRepository;
import com.ecommerce.core.entity.BasketEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.core.util.CouponDiscountCalculator;
import lombok.Builder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.ecommerce.core.exception.faultCode.BasketFaultCode.BASKET_NOT_FOUND;
import static com.ecommerce.core.exception.faultCode.UserFaultCode.USER_NOT_FOUND;

@Builder
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;
    private final BasketItemService basketItemService;
    private final BasketCalculator basketCalculator;
    private final CouponDiscountCalculator couponDiscountCalculator;
    private final RestTemplate restTemplate;

    @Override
    public BasketCreateResponse createBasket(BasketCreateRequest basketCreateRequest) {
        Long userId = basketCreateRequest.getUserId();
        boolean existsUser = existsUser(userId);
        if (!existsUser) {
            throw new BusinessException(USER_NOT_FOUND, userId);
        }
        BasketEntity basketEntity = basketRepository.save(basketMapper.mapToEntity(basketCreateRequest));
        return basketMapper.mapToResponse(basketEntity);
    }

    @Override
    public Basket getBasketById(Long basketId) {
        Optional<BasketEntity> optionalBasketEntity = basketRepository.findById(basketId);
        if (!optionalBasketEntity.isPresent()) {
            throw new BusinessException(BASKET_NOT_FOUND, basketId);
        }
        BasketEntity basketEntity = optionalBasketEntity.get();
        Basket basket = basketMapper.mapToDTO(basketEntity);

        List<BasketItem> basketItems = basketItemService.getBasketItemsByCartId(basketId);
        basket.setBasketItems(basketItems);

        BigDecimal totalPrice = basketCalculator.calculateBasketTotalPrice(basket.getBasketItems());
        basket.setTotalPrice(totalPrice);

        BigDecimal salePrice = basketCalculator.calculateBasketSalePrice(basket.getBasketItems());
        basket.setSalePrice(salePrice);

        Integer totalQuantity = getTotalQuantity(basket);
        basket.setTotalQuantity(totalQuantity);

        Optional<Coupon> optionalCoupon = getCouponByTotalPrice(basket.getTotalPrice());
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            basket.setCoupon(coupon);
            applyDiscountIfCouponExists(basket);
        } else {
            BigDecimal discount = totalPrice.subtract(salePrice);
            basket.setDiscount(discount);
        }
        return basket;
    }

    private Optional<Coupon> getCouponByTotalPrice(BigDecimal totalPrice) {
        URI uri = UriComponentsBuilder.fromUriString("http://discount/api/v1/coupons/thresholdFilter").queryParam("price", String.valueOf(totalPrice)).build().toUri();
        ResponseEntity<Coupon> response = restTemplate.exchange(uri, HttpMethod.GET, null, Coupon.class);
        return Optional.ofNullable(response.getBody());
    }

    private Integer getTotalQuantity(Basket basket) {
        return basket.getBasketItems().stream()
                .map(BasketItem::getQuantity)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private boolean existsUser(Long userId) {
        // request account-service, default true.
        return userId != null;
    }

    private void applyDiscountIfCouponExists(Basket basket) {
        Coupon coupon = basket.getCoupon();

        BigDecimal discount = couponDiscountCalculator.calculateCouponDiscount(coupon, basket.getSalePrice());

        BigDecimal totalDiscount = basket.getTotalPrice().subtract(basket.getSalePrice()).add(discount);
        basket.setDiscount(totalDiscount);

        BigDecimal totalSale = basket.getTotalPrice().subtract(totalDiscount);
        basket.setSalePrice(totalSale);
    }
}
